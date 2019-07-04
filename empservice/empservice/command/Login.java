package in.vamsoft.empservice.command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.vamsoft.empservice.sql.ConnectionPool;
import in.vamsoft.empservice.sql.SQLHolder;
import in.vamsoft.empservice.utils.CommandUtil;
import in.vamsoft.empservice.utils.Employee;
import in.vamsoft.empservice.utils.UserNotFoundException;

import org.apache.log4j.Logger;



public class Login implements Command{

    public static Logger logger;
    static{
        logger = Logger.getLogger(CommandUtil.MYLOGGER);	
    }


    @Override
    public String execute(HttpServletRequest request,
            HttpServletResponse response) throws SQLException {

        logger.debug(CommandUtil.LOGINSTRART);

        ServletContext context = request.getServletContext();
        HttpSession session=request.getSession();

        String emailId =request.getParameter(CommandUtil.EMAILID);
        String password =request.getParameter(CommandUtil.PASSWORD);

        Employee emp=new Employee(emailId, password);

        emp=getLogin(emp,request);


        Date date=new Date();
        SimpleDateFormat ft=new SimpleDateFormat(CommandUtil.DATEFORMAT);
        String s=ft.format(date);
        session.setAttribute(CommandUtil.DATE, s);

        Date dateTime=new Date();
        Date timeStampDate=new java.sql.Timestamp(dateTime.getTime());
        session.setAttribute(CommandUtil.DATETIME, timeStampDate);

        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader(CommandUtil.U_AGENT);
        boolean isLoggedInUser = checkLoggedInUser(context, emailId);

        if(emp.getEmpName()!=null)
        {	
            if(emp.getRole().equals(CommandUtil.RADMIN)){

                if(isLoggedInUser==false){

                    session.setAttribute(CommandUtil.SEMP, emp);
                    session.setAttribute(
                            CommandUtil.SEMP_ID, emp.getEmpId());
                    session.setAttribute(
                            CommandUtil.SEMP_NAME, emp.getEmpName());
                    session.setAttribute(CommandUtil.SEMP_IP, ipAddress);
                    session.setAttribute(
                            CommandUtil.SEMP_USER_AGENT, userAgent);
                    loginHistory(emp.getEmpId(),emp.getEmpName(),
                            ipAddress,userAgent,timeStampDate, request);
                    return CommandUtil.ADMINSUCCESS;

                }else {

                    request.setAttribute(CommandUtil.ALREADY_LOGIN_ERROR,
                            CommandUtil.USER_LOGIN_ERROR_MSG);
                    return CommandUtil.FAILURE;

                }

            }else
            {
                if(isLoggedInUser==false){

                    session.setAttribute(CommandUtil.SEMP, emp);
                    session.setAttribute(
                            CommandUtil.SEMP_ID, emp.getEmpId());
                    session.setAttribute(
                            CommandUtil.SEMP_NAME, emp.getEmpName());
                    session.setAttribute(CommandUtil.SEMP_IP, ipAddress);
                    session.setAttribute(
                            CommandUtil.SEMP_USER_AGENT, userAgent);
                    loginHistory(emp.getEmpId(),emp.getEmpName(),
                            ipAddress,userAgent,timeStampDate, request);
                    return CommandUtil.USERSUCCESS;

                }else {

                    request.setAttribute(CommandUtil.ALREADY_LOGIN_ERROR,
                            CommandUtil.USER_LOGIN_ERROR_MSG);
                    return CommandUtil.FAILURE;

                }
            }

        }
        else
        {

            int attemptCount = checkLoginAttempts(session);
            if(attemptCount>=3){

                try {

                    userDisable(emailId, context);

                } catch (UserNotFoundException e) {

                    e.printStackTrace();

                }

                request.setAttribute(CommandUtil.BLOCKED_MSG,
                        CommandUtil.U_BLOCKED_MSG);
                return CommandUtil.FAILURE;

            }

            request.setAttribute(CommandUtil.LOGIN_ATTEMPT_MSG,
                    CommandUtil.U_LOGIN_ATTEMPT_MSG1 + 
                    (3-attemptCount) + CommandUtil.ATTEMPT_LEFT);
            return CommandUtil.FAILURE;
        }
    }


    private void loginHistory(int empId, String empName, String ipAddress,
            String userAgent, Date timeStampDate,
            HttpServletRequest request) {

        PreparedStatement statement=null;

        ServletContext context=request.getServletContext();
        ConnectionPool connectionPool=(ConnectionPool)
                context.getAttribute(CommandUtil.CONNECTIONPOOL);
        Connection connection=(Connection)connectionPool.getConnection();



        try {
            statement=connection.prepareStatement(
                    SQLHolder.INSERTLOGINQUERY);
            statement.setInt(1,empId);
            statement.setString(2, empName);
            statement.setTimestamp(3,  (Timestamp) timeStampDate);
            statement.setString(4, ipAddress);
            statement.setString(5, userAgent);
            statement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }
        finally{
            try {

                statement.close();

            } catch (SQLException e) {

                logger.debug(e);
            }
        }


    }


    private void userDisable(String emailId, ServletContext context)
            throws UserNotFoundException {

        int rows=0;
        PreparedStatement statement=null;
        ConnectionPool connectionPool=(ConnectionPool) 
                context.getAttribute(CommandUtil.CONNECTIONPOOL);
        Connection connection=(Connection)connectionPool.getConnection();

        try {

            statement=connection.prepareStatement(
                    SQLHolder.DISABLEEMPBYQUERY);
            statement.setString(1, emailId);
            rows = statement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }
        if (rows <0){
            throw new UserNotFoundException();
        }


    }


    private int checkLoginAttempts(HttpSession session) {

        Object count = session.getAttribute
                (CommandUtil.FAILED_LOGIN_ATTEMPT);
        Integer counter= null; 
        if(count!=null){
            counter = (Integer) count;
        }else{
            counter = new Integer(0);
        }

        counter++;

        session.setAttribute(CommandUtil.FAILED_LOGIN_ATTEMPT, counter);

        return counter;
    }


    private boolean checkLoggedInUser
    (ServletContext context, String emailId) {

        Object users=context.getAttribute(CommandUtil.LOGIN_USER);

        Set<String> loggedUsers= null;
        boolean result;

        if(users!=null){

            loggedUsers= (Set<String>) users;

        }else {

            loggedUsers= new HashSet<>();

        }

        result = loggedUsers.add(emailId);

        logger.debug(loggedUsers.toString());

        context.setAttribute(CommandUtil.LOGIN_USER, loggedUsers);

        //negate result to get the desired effect
        //set returns true if the element is added successfully
        return !result;

    }


    protected Employee getLogin(Employee emp,HttpServletRequest request) {

        ResultSet rs=null;
        PreparedStatement statement=null;

        ServletContext context=request.getServletContext();
        ConnectionPool connectionPool=(ConnectionPool)
                context.getAttribute(CommandUtil.CONNECTIONPOOL);
        Connection connection=(Connection)connectionPool.getConnection();

        try {
            statement=connection.prepareStatement(SQLHolder.LOGINQUERY);
            statement.setString(1,emp.getEmailId());
            statement.setString(2,emp.getPwd());
            rs=statement.executeQuery();
            while(rs.next())
            {

                emp.setEmpId(rs.getInt(CommandUtil.EMPID));
                emp.setEmpName(rs.getString(CommandUtil.EMPNAME));
                emp.setPhoneNo(rs.getString(CommandUtil.PHNO));
                emp.setEmailId(rs.getString(CommandUtil.EMAILID));
                emp.setPwd(rs.getString(CommandUtil.PASSWORD));
                emp.setRole(rs.getString(CommandUtil.ROLE));
                emp.setDesignation(rs.getString(CommandUtil.DESIGNATION));
                emp.setDoj(rs.getDate(CommandUtil.DOJ));
                emp.setDol(rs.getDate(CommandUtil.DOL));
                emp.setStatus(rs.getString(CommandUtil.STATUS));
                connectionPool.returnPoolConnection(connection);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try {
                statement.close();
                rs.close();

            } catch (SQLException e) {
                logger.debug(e);
            }
        }
        return emp;
    }
}
