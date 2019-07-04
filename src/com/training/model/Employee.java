package com.training.model;

public class Employee {

	private int employeeId;
	private String employeeName;
	private long phoneNumber;
	private String userName;
	private String passWord;
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Employee(String employeeName, long phoneNumber, String userName, String passWord) {
		super();
		this.employeeName = employeeName;
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.passWord = passWord;
	}

	

	public Employee(String userName, String passWord) {
		super();
		this.userName = userName;
		this.passWord = passWord;
	}



	public Employee(int employeeId, String employeeName, long phoneNumber, String userName, String passWord) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.passWord = passWord;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", employeeName=" + employeeName + ", phoneNumber=" + phoneNumber
				+ ", userName=" + userName + ", passWord=" + passWord + "]";
	}
	
	
}
