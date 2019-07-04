package in.vamsoft.emscrm.utils;

import java.util.Date;
public class Employee {
	private  int empId;
	private String empName;
	private String phoneNo;
	private String emailId;
	private String pwd;
	private String designation;
	private String role;
	private Date doj;
	private String status;

	public Employee(int empId, String empName, String phoneNo, String emailId,
			String pwd, String designation, String role, Date doj,
			String status) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.phoneNo = phoneNo;
		this.emailId = emailId;
		this.pwd = pwd;
		this.designation = designation;
		this.role = role;
		this.doj = doj;
		this.status = status;
	}

	public Employee(String emailId, String pwd) {
		super();
		this.emailId = emailId;
		this.pwd = pwd;
	}

	public Employee(int hidEmpId) {
		super();
		this.empId=hidEmpId;
	}

	public Employee() {

	}

	public Employee(String empName2) {
		super();
		this.empName=empName2;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName
				+ ", phoneNo=" + phoneNo + ", emailId=" + emailId + ", pwd="
				+ pwd + ", designation=" + designation + ", role=" + role
				+ ", doj=" + doj + ", status=" + status + "]";
	}


}
