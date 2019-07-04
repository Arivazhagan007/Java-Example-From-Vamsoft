package in.vamsoft.empservice.command;

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
import javax.servlet.http.HttpSession;

import in.vamsoft.empservice.sql.ConnectionPool;
import in.vamsoft.empservice.sql.SQLHolder;
import in.vamsoft.empservice.utils.CommandUtil;
import in.vamsoft.empservice.utils.Employee;

import org.apache.log4j.Logger;

public class UpdateEmployee implements Command{

	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		String uri = request.getRequestURI();

		String[] arr = uri.split("/");

		String path=arr[arr.length-1];
		System.out.println(path);
		HttpSession session=request.getSession();
		Date date=new Date();
		SimpleDateFormat ft=new SimpleDateFormat(CommandUtil.DATEFORMAT);
		SimpleDateFormat dateFormat = new SimpleDateFormat(CommandUtil.DFORMAT);
		String s=ft.format(date);
		session.setAttribute(CommandUtil.DATE, s);


		int empId=Integer.parseInt(request.getParameter(CommandUtil.HEMPID));
		String phNo=request.getParameter(CommandUtil.PHNO);
		String designation=request.getParameter(CommandUtil.DESIGNATION);
		String role=request.getParameter(CommandUtil.ROLE);
		String strdol=request.getParameter(CommandUtil.DOL);

		java.sql.Date dolDate=null;
		try {
			dolDate = new java.sql.Date(dateFormat.parse(strdol).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int rows=updateEmpDetails(empId,phNo,designation,role,dolDate,request);
		if(rows>0){
			Employee emp=new Employee(empId);
			emp=getEmpById(emp,request);
			logger.debug(CommandUtil.AFTERUPDATE);
			session.setAttribute(CommandUtil.SUAEMPBYID, emp);
			if(emp.getPhoneNo()!=null||emp.getDesignation()!=null||emp.getRole()!=null||emp.getDol()!=null){
				return CommandUtil.SUCCESS;
			}
			else{
				request.setAttribute(CommandUtil.E_ERROR, CommandUtil.EDETAIL_ERROR);
				return CommandUtil.FAILURE;
			}
		}else{
			request.setAttribute(CommandUtil.E_ERROR, CommandUtil.EDETAIL_ERROR);
			return CommandUtil.FAILURE;
		}

	}


	private Employee getEmpById(Employee emp, HttpServletRequest request) {
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
					emp.setDesignation(rs.getString(CommandUtil.DESIGNATION));
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


	private int updateEmpDetails(int empId, String phNo, String designation,
			String role, Date dolDate, HttpServletRequest request) {
		int rows=0;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.UPDATEEMPQUERY);
			statement.setString(1,phNo);
			statement.setString(2,designation);
			statement.setString(3, role);
			statement.setDate(4, (java.sql.Date) dolDate);
			statement.setInt(5, empId);
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
