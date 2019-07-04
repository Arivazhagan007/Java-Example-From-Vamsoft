package in.vamsoft.empservice.command;

import in.vamsoft.empservice.sql.ConnectionPool;
import in.vamsoft.empservice.sql.SQLHolder;
import in.vamsoft.empservice.utils.CommandUtil;
import in.vamsoft.empservice.utils.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class DisableEmployee implements Command{

	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		HttpSession session=request.getSession();
		int empId=Integer.parseInt(request.getParameter(CommandUtil.HEMPID));

		int rows=disableEmployee(empId,request);
		if(rows>0){
			Employee emp=new Employee(empId);
			emp=getEmpById(emp,request);
			logger.debug(CommandUtil.AFTERDISABLE);
			session.setAttribute(CommandUtil.SDEMPBYID, emp);
			if(emp.getDesignation()!=null){

				return CommandUtil.SUCCESS;
			}
			else{
				request.setAttribute(CommandUtil.DISABLEERROR, CommandUtil.VEMPI_ERROR);
				return CommandUtil.FAILURE;
			}
		}else{
			request.setAttribute(CommandUtil.DISABLEERROR, CommandUtil.VEMPI_ERROR);
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


	private int disableEmployee(int empId, HttpServletRequest request) {
		int rows=0;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.DISABLEEMPQUERY);
			statement.setInt(1, empId);
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
