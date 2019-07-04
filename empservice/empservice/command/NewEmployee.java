package in.vamsoft.empservice.command;

import in.vamsoft.empservice.sql.ConnectionPool;
import in.vamsoft.empservice.sql.SQLHolder;
import in.vamsoft.empservice.utils.CommandUtil;
import in.vamsoft.empservice.utils.Employee;
import in.vamsoft.empservice.utils.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class NewEmployee implements Command{

    public static Logger logger;
    static{
        logger = Logger.getLogger(CommandUtil.MYLOGGER);	
    }


    @Override
    public String execute(HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CommandUtil.DFORMAT);
        String empName=request.getParameter(CommandUtil.EMPNAME);
        String emailId=request.getParameter(CommandUtil.EMAILID);
        String pwd=request.getParameter(CommandUtil.PASSWORD);
        String phNo=request.getParameter(CommandUtil.PHNO);
        String designation=request.getParameter(CommandUtil.DESIGNATION);
        String role=request.getParameter(CommandUtil.ROLE);
        String strdoj=request.getParameter(CommandUtil.DOJ);
        java.sql.Date dojDate=null;

        try {
            dojDate = new java.sql.Date(dateFormat.parse(strdoj).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String strdol=request.getParameter(CommandUtil.DOL);
        java.sql.Date dolDate=null;

        try {
            dolDate = new java.sql.Date(dateFormat.parse(strdol).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String status=request.getParameter(CommandUtil.STATUS);
        int rows=newEmployee(empName,emailId,pwd,phNo,designation,role,dojDate,dolDate,status,request);
        if(rows>0){
            Employee emp=lastInsertEmpId(request);
            if(emp!=null){

                int eid=emp.getEmpId();
                String password=emp.getPwd();
                String erole=emp.getRole();

                Role vrole=viewRoleByName(erole,request);

                if(vrole!=null){

                    int rid=vrole.getRoleId();

                    int row=insertUserRole(eid,rid,request);

                    insertPassword(eid,password,request);

                    if(row>0){
                        request.setAttribute(CommandUtil.NEMP_SUCCESS, CommandUtil.NEWEMP_SUCCESS);
                        return CommandUtil.SUCCESS;
                    }
                    else{
                        request.setAttribute(CommandUtil.NEMP_ERROR, CommandUtil.NEWEMP_ERROR);
                        return CommandUtil.USER_ROLEFAILURE;
                    }
                }
            }

        }
        else{
            request.setAttribute(CommandUtil.NE_ERROR, CommandUtil.NEP_ERROR);
            return CommandUtil.FAILURE;
        }
        request.setAttribute(CommandUtil.NE_ERROR, CommandUtil.NEP_ERROR);
        return CommandUtil.FAILURE;
    }


    private void insertPassword(int eid, String password,
            HttpServletRequest request) {

        PreparedStatement statement=null;

        ServletContext context=request.getServletContext();
        ConnectionPool connectionPool=(ConnectionPool)
                context.getAttribute(CommandUtil.CONNECTIONPOOL);
        Connection connection=(Connection)connectionPool.getConnection();

        try {
            statement=connection.prepareStatement
                    (SQLHolder.INSERTPASSWORDQUERY);
            statement.setInt(1,eid);
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


    private int insertUserRole(int eid, int rid, HttpServletRequest request) {
        int rows=0;
        PreparedStatement statement=null;

        ServletContext context=request.getServletContext();
        ConnectionPool connectionPool=(ConnectionPool)
                context.getAttribute(CommandUtil.CONNECTIONPOOL);
        Connection connection=(Connection)connectionPool.getConnection();

        try {
            statement=connection.prepareStatement(SQLHolder.INSERTUSER_ROLEQUERY);
            statement.setInt(1,eid);
            statement.setInt(2,rid);
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


    private Role viewRoleByName(String erole, HttpServletRequest request) {
        Role role=null;
        ResultSet rs=null;
        PreparedStatement statement=null;

        ServletContext context=request.getServletContext();
        ConnectionPool connectionPool=(ConnectionPool)
                context.getAttribute(CommandUtil.CONNECTIONPOOL);
        Connection connection=(Connection)connectionPool.getConnection();

        try {
            statement=connection.prepareStatement(SQLHolder.ROLEBYNAMEQUERY);
            statement.setString(1, erole);
            rs=statement.executeQuery();


            while(rs.next())
            {

                role=new Role();
                role.setRoleId(rs.getInt(CommandUtil.ROLEID));
                role.setRoleName(rs.getString(CommandUtil.ROLENAME));
                role.setDescr(rs.getString(CommandUtil.RDESCR));
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


        return role;
    }


    private Employee lastInsertEmpId(HttpServletRequest request) {
        Employee emp=null;
        ResultSet rs=null;
        PreparedStatement statement=null;

        ServletContext context=request.getServletContext();
        ConnectionPool connectionPool=(ConnectionPool)
                context.getAttribute(CommandUtil.CONNECTIONPOOL);
        Connection connection=(Connection)connectionPool.getConnection();

        try {
            statement=connection.prepareStatement(SQLHolder.RETRIVELASTINSERTEMPQUERY);
            rs=statement.executeQuery();


            while(rs.next())
            {
                emp=new Employee();
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


    private int newEmployee(String empName, String emailId, String pwd,
            String phNo, String designation, String role,
            Date dojDate, Date dolDate, String status,
            HttpServletRequest request) {
        int rows=0;
        PreparedStatement statement=null;

        ServletContext context=request.getServletContext();
        ConnectionPool connectionPool=(ConnectionPool)
                context.getAttribute(CommandUtil.CONNECTIONPOOL);
        Connection connection=(Connection)connectionPool.getConnection();

        try {
            statement=connection.prepareStatement(SQLHolder.INSERTEMPLOYEEQUERY);
            statement.setString(1,empName);
            statement.setString(2,emailId);
            statement.setString(3,pwd);
            statement.setString(4,phNo);
            statement.setString(5,designation);
            statement.setString(6, role);
            statement.setDate(7, (java.sql.Date) dojDate);
            statement.setDate(8, (java.sql.Date) dolDate);
            statement.setString(9, status);
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
}
