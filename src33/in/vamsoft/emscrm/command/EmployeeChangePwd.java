package in.vamsoft.emscrm.command;


import in.vamsoft.emscrm.sql.ConnectionPool;
import in.vamsoft.emscrm.sql.SQLHolder;
import in.vamsoft.emscrm.utils.CommandUtil;

import in.vamsoft.emscrm.utils.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class EmployeeChangePwd implements Command{

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
				(CommandUtil.SEMP_ID));

		Employee emp = new Employee(hidEmpId);

		emp=getEmpId(emp,request);

		if(emp!=null){
			if(emp.getPwd().equals(oldPassword)){

				int row = changePwd(password, emp.getEmpId(), request);
				if(row>0){
					request.setAttribute(CommandUtil.CHANGEPWDMSG,
							CommandUtil.U_CHANGEPWDMSG);
					return CommandUtil.SUCCESS;
				}
				request.setAttribute(CommandUtil.CHANGEPWDERROR, 
						CommandUtil.U_CHANGEPWDERROR);
				return CommandUtil.FAILURE;
			}
			request.setAttribute(CommandUtil.CHANGEPWDERROR, 
					CommandUtil.U_CHANGEPWDERROR);
			return CommandUtil.FAILURE;
		}else{

			request.setAttribute(CommandUtil.CHANGEPWDERROR, 
					CommandUtil.U_CHANGEPWDERROR);
			return CommandUtil.FAILURE;
		}


	}

	private int changePwd(String password, int empId,
			HttpServletRequest request) {

		int rows=0;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement
					(SQLHolder.CHANGEEMPPWDQUERY);
			statement.setString(1,password);
			statement.setInt(2,empId);
			rows=statement.executeUpdate();
			System.out.println(statement);
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

	private Employee getEmpId(Employee emp,
			HttpServletRequest request) {

		ResultSet rs=null;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.VIEWBYEMPIDQUERY);
			statement.setInt(1,emp.getEmpId());
			rs=statement.executeQuery();
			while(rs.next())
			{
				emp.setEmpId(rs.getInt(CommandUtil.EMPID));
				emp.setEmpName(rs.getString(CommandUtil.EMPNAME));
				
				emp.setPhoneNo(rs.getString(CommandUtil.PHNO));
				emp.setEmailId(rs.getString(CommandUtil.EMAILID));
				emp.setPwd(rs.getString(CommandUtil.PASSWORD));
				
				emp.setDoj(rs.getDate(CommandUtil.DOJ));
				
				
				emp.setStatus(rs.getString(CommandUtil.STATUS));
				connectionPool.returnPoolConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return emp;
	}
}
