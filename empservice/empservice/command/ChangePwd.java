package in.vamsoft.empservice.command;

import in.vamsoft.empservice.sql.ConnectionPool;
import in.vamsoft.empservice.sql.SQLHolder;
import in.vamsoft.empservice.utils.CommandUtil;
import in.vamsoft.empservice.utils.Employee;
import in.vamsoft.empservice.utils.PasswordDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class ChangePwd implements Command{

    public static Logger logger;
    static{
        logger = Logger.getLogger(CommandUtil.MYLOGGER);	
    }
    @Override
    public String execute(HttpServletRequest request,
            HttpServletResponse response) throws SQLException {

        HttpSession session=request.getSession();
        Date date=new Date();
        SimpleDateFormat ft=new SimpleDateFormat(CommandUtil.DATEFORMAT);
        String s=ft.format(date);

        String oldPassword =request.getParameter(CommandUtil.OLDPASS);
        String password =request.getParameter(CommandUtil.PASSWORD);
        int hidEmpId =Integer.parseInt(request.getParameter
                (CommandUtil.HEMPID));

        Employee emp=new Employee(hidEmpId);

        emp=getEmpById(emp,request);

        if(emp!=null){

            List<PasswordDetails> details=passwordCheck(emp,oldPassword,
                    request);
            if(details!=null){

                for (PasswordDetails passwordDetails : details) {

                    if(passwordDetails.getPwd().equals(password)){

                        request.setAttribute(CommandUtil.CHANGEPWDERROR,
                                CommandUtil.U_CHANGEPWDERROR);
                        return CommandUtil.FAILURE;

                    }

                    emp = passwordChangeFunction(password,emp,request);

                    if(emp!=null){

                        session.setAttribute(CommandUtil.DATE, s);
                        logger.debug(CommandUtil.CHANGEPWD);
                        session.setAttribute(CommandUtil.SEMPBYID, emp);

                        if(emp.getRole().equals(CommandUtil.RADMIN)){

                            request.setAttribute(CommandUtil.CHANGEPWDMSG,
                                    CommandUtil.U_CHANGEPWDMSG);
                            return CommandUtil.ADMINSUCCESS;

                        }else
                        {  
                            request.setAttribute(CommandUtil.CHANGEPWDMSG,
                                    CommandUtil.U_CHANGEPWDMSG);
                            return CommandUtil.USERSUCCESS;
                        }
                    }else{
                        request.setAttribute(CommandUtil.CHANGEPWDERROR,
                                CommandUtil.U_CHANGEPWDERROR);
                        return CommandUtil.FAILURE;
                    }
                }
            }
        }

        request.setAttribute(CommandUtil.CHANGEPWDERROR, 
                CommandUtil.U_CHANGEPWDERROR);
        return CommandUtil.FAILURE;

    }





    private Employee passwordChangeFunction(String password, Employee emp,
            HttpServletRequest request) {

        int row = changePwd(password, emp.getEmpId(), request);
        insertPassword(emp.getEmpId(),password,request);
        Employee employee=null;
        if(row>0){

            employee=getEmpById(emp,request);
        }

        return employee;
    }


    private void insertPassword(int id, String password,
            HttpServletRequest request) {

        PreparedStatement statement=null;

        ServletContext context=request.getServletContext();
        ConnectionPool connectionPool=(ConnectionPool)
                context.getAttribute(CommandUtil.CONNECTIONPOOL);
        Connection connection=(Connection)connectionPool.getConnection();

        try {
            statement=connection.prepareStatement
                    (SQLHolder.INSERTPASSWORDQUERY);
            statement.setInt(1,id);
            statement.setString(2,password);
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


    private List<PasswordDetails> checkPasswordHistory(int id, 
            HttpServletRequest request) {

        List<PasswordDetails> details = new ArrayList<>();
        PasswordDetails passwordDetails= null;
        ResultSet rs=null;
        PreparedStatement statement=null;

        int empId = 0;
        String pwd = null;

        ServletContext context=request.getServletContext();
        ConnectionPool connectionPool=(ConnectionPool)
                context.getAttribute(CommandUtil.CONNECTIONPOOL);
        Connection connection=(Connection)connectionPool.getConnection();

        try {
            statement=connection.prepareStatement
                    (SQLHolder.CHECKPWDHISTORYQRY);
            statement.setInt(1, id);
            rs=statement.executeQuery();
            while(rs.next())
            {
                passwordDetails=new PasswordDetails();
                empId=rs.getInt(CommandUtil.EMPID);
                pwd=rs.getString(CommandUtil.PASSWORD);

                passwordDetails=new PasswordDetails(empId,pwd);

                details.add(passwordDetails);
                connectionPool.returnPoolConnection(connection);
            }

        } 
        catch (SQLException e) {

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
        return details;

    }


    private int changePwd(String oldPassword, int hidEmpId,
            HttpServletRequest request) {
        int rows=0;
        PreparedStatement statement=null;

        ServletContext context=request.getServletContext();
        ConnectionPool connectionPool=(ConnectionPool)
                context.getAttribute(CommandUtil.CONNECTIONPOOL);
        Connection connection=(Connection)connectionPool.getConnection();

        try {
            statement=connection.prepareStatement
                    (SQLHolder.CHANGEPWDQUERY);
            statement.setString(1,oldPassword);
            statement.setInt(2,hidEmpId);
            rows=statement.executeUpdate();
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
        return rows;
    }


    protected Employee getEmpById(Employee emp, 
            HttpServletRequest request) {
        ResultSet rs=null;
        PreparedStatement statement=null;

        ServletContext context=request.getServletContext();
        ConnectionPool connectionPool=(ConnectionPool)
                context.getAttribute(CommandUtil.CONNECTIONPOOL);
        Connection connection=(Connection)connectionPool.getConnection();

        try {
            statement=connection.prepareStatement(SQLHolder.VIEWBYIDQUERY);
            statement.setInt(1,emp.getEmpId());
            rs=statement.executeQuery();


            while(rs.next())
            {
                if((emp.getEmpId()==(rs.getInt(CommandUtil.EMPID))))
                {
                    emp.setEmpId(rs.getInt(CommandUtil.EMPID));
                    emp.setEmpName(rs.getString(CommandUtil.EMPNAME));
                    emp.setPhoneNo(rs.getString(CommandUtil.PHNO));
                    emp.setEmailId(rs.getString(CommandUtil.EMAILID));
                    emp.setPwd(rs.getString(CommandUtil.PASSWORD));
                    emp.setRole(rs.getString(CommandUtil.ROLE));
                    emp.setDesignation(rs.getString
                            (CommandUtil.DESIGNATION));
                    emp.setDoj(rs.getDate(CommandUtil.DOJ));
                    emp.setDol(rs.getDate(CommandUtil.DOL));
                    emp.setStatus(rs.getString(CommandUtil.STATUS));
                    connectionPool.returnPoolConnection(connection);
                }
                return emp;
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

    public List<PasswordDetails> passwordCheck(Employee emp, 
            String oldPassword, HttpServletRequest request){

        String dbPwd=emp.getPwd();
        int id= emp.getEmpId();
        List<PasswordDetails> details=null;
        if(dbPwd.equals(oldPassword)){
            details = checkPasswordHistory(id,request);
        }
        return details;
    }

}
