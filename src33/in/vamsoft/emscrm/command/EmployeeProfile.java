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

public class EmployeeProfile implements Command{
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


		int hidEmpId =Integer.parseInt(request.getParameter
				(CommandUtil.HIDEMPID));

		Employee employee = new Employee(hidEmpId);

		employee=getEmpId(employee,request);

		if(employee!=null){
			session.setAttribute(CommandUtil.PROFILEDETAILS, employee);

			return CommandUtil.SUCCESS;

		}else{
			request.setAttribute(CommandUtil.PROFILE_ERROR, CommandUtil.PROFILE_ERROR_MSG);
			return CommandUtil.FAILURE;
		}


	}


	private Employee getEmpId(Employee employee,
			HttpServletRequest request) {

		ResultSet rs=null;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.VIEWBYEMPIDQUERY);
			statement.setInt(1,employee.getEmpId());
			rs=statement.executeQuery();
			while(rs.next())
			{
				employee.setEmpId(rs.getInt(CommandUtil.EMPID));
				employee.setEmpName(rs.getString(CommandUtil.EMPNAME));
			
				employee.setPhoneNo(rs.getString(CommandUtil.PHNO));
				employee.setEmailId(rs.getString(CommandUtil.EMAILID));
				employee.setPwd(rs.getString(CommandUtil.PASSWORD));
				employee.setDesignation(rs.getString(CommandUtil.DESIGNATION));
				employee.setRole(rs.getString(CommandUtil.ROLE));
				
				
				employee.setDoj(rs.getDate(CommandUtil.DOJ));
				
				employee.setStatus(rs.getString(CommandUtil.STATUS));
				connectionPool.returnPoolConnection(connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return employee;
	}
}

