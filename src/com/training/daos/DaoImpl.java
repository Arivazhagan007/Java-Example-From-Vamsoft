package com.training.daos;

import java.sql.*;

import com.training.iface.Dao;
import com.training.model.Customer;
import com.training.model.Employee;
import com.training.model.Tour;

public class DaoImpl implements Dao {

	private Connection con;

	public DaoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DaoImpl(Connection con) {
		super();
		this.con = con;
	}

	@Override
	public int addCustomer(Customer customer) {
		
	 String custSql = "insert into Customer values(?,?,?,?,?)";
	 PreparedStatement custpreparedStatement = null;
	 int addValue = 0;
	 
	 try {
		custpreparedStatement = con.prepareStatement(custSql);
		custpreparedStatement.setInt(1, customer.getCustomerId());
		custpreparedStatement.setString(2, customer.getCustomerName());
		custpreparedStatement.setLong(3, customer.getPhoneNumber());
		custpreparedStatement.setString(4, customer.getUserName());
		custpreparedStatement.setString(5, customer.getPassWord());
		addValue = custpreparedStatement.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		try {
			custpreparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		return addValue;
	}

	@Override
	public boolean customerLoginValidate(Customer customer) {
		boolean result=false;
		String sql="select * from Customer where username=? AND password=?";
		PreparedStatement validatePreparedStatement=null;
		
		try {
			validatePreparedStatement=con.prepareStatement(sql);
			
			validatePreparedStatement.setString(1, customer.getUserName());
			validatePreparedStatement.setString(2, customer.getPassWord());
			
			ResultSet rs=validatePreparedStatement.executeQuery();
			if(rs.next()){
				result=true;
			}
			
		}catch (Exception e) {
		e.printStackTrace();
		}
		finally {
			try {
				validatePreparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public int addEmployee(Employee employee) {
		String empSql = "insert into Emploee values(?,?,?,?,?)";
		 PreparedStatement custpreparedStatement = null;
		 int addValue = 0;
		 
		 try {
			custpreparedStatement = con.prepareStatement(empSql);
			custpreparedStatement.setInt(1, employee.getEmployeeId());
			custpreparedStatement.setString(2, employee.getEmployeeName());
			custpreparedStatement.setLong(3, employee.getPhoneNumber());
			custpreparedStatement.setString(4, employee.getUserName());
			custpreparedStatement.setString(5, employee.getPassWord());
			addValue = custpreparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				custpreparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			return addValue;
	}

	@Override
	public boolean employeeLoginValidate(Employee employee) {
		boolean result=false;
		String empSql="select * from Employee where username=? AND password=?";
		PreparedStatement validatePreparedStatement=null;
		
		try {
			validatePreparedStatement=con.prepareStatement(empSql);
			
			validatePreparedStatement.setString(1, employee.getUserName());
			validatePreparedStatement.setString(2, employee.getPassWord());
			
			ResultSet rs=validatePreparedStatement.executeQuery();
			if(rs.next()){
				result=true;
			}
			
		}catch (Exception e) {
		e.printStackTrace();
		}
		finally {
			try {
				validatePreparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public int addTour(Tour tour) {
		
		String tourSql = "insert into Tour values(?,?,?,?,?,?,?,?,?)";
		 PreparedStatement tourPreparedStatement = null;
		 int addValue = 0;
		 
		 try {
			tourPreparedStatement = con.prepareStatement(tourSql);
			tourPreparedStatement.setInt(1, tour.getTourId());
			tourPreparedStatement.setString(2, tour.getTourCode());
			tourPreparedStatement.setString(3, tour.getTourName());
			tourPreparedStatement.setString(4, tour.getBoardingPlace());
			tourPreparedStatement.setString(5, tour.getDestinationPlace());
			tourPreparedStatement.setDate(6, Date.valueOf(tour.getStartingDate()));
			tourPreparedStatement.setDate(7, Date.valueOf(tour.getEndingDate()));
			tourPreparedStatement.setString(8, tour.getPlacesCovered());
			tourPreparedStatement.setDouble(9, tour.getAmountPerPerson());
			addValue = tourPreparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addValue;
	}


	
	

	
	
	
	
	
}
