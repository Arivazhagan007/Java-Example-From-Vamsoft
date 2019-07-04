package com.training.model;

import java.math.BigInteger;

public class Customer {

	private int customerId;
	private String customerName;
	private long phoneNumber;
	private String userName;
	private String passWord;
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Customer(String customerName, long phoneNumber, String userName, String passWord) {
		super();
		this.customerName = customerName;
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.passWord = passWord;
	}

	
	
	public Customer(String userName, String passWord) {
		super();
		this.userName = userName;
		this.passWord = passWord;
	}

	public Customer(int customerId, String customerName, long phoneNumber, String userName, String passWord) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.passWord = passWord;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", phoneNumber=" + phoneNumber
				+ ", userName=" + userName + ", passWord=" + passWord + "]";
	}
	
}
