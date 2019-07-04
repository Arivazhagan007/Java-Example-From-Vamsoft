package in.vamsoft.emscrm.utils;

import java.sql.Timestamp;
import java.util.Date;

public class CustomerDetails {

	private int customerId;
	private String customerName;
	private String firstName;
	private String lastName;
	private String emailId;
	private String password;
	private String phoneNo;
	private String altPhoneNo;
	private String securityQuestion;
	private String securityAnswer;
	private String altEmailId;
	private Date dob;
	private int pincode;
	private String state;
	private String country;
	private Timestamp dtStamp;
	private String address;
	private String status;

	public CustomerDetails(String customerName2, String phoneNo2,
			String emailId2) {

		this.customerName=customerName2;
		this.emailId=emailId2;
		this.phoneNo=phoneNo2;

	}
	public CustomerDetails() {
		
	}
	public CustomerDetails(String emailId2, String password2) {
		this.emailId = emailId2;
		this.password = password2;
	}
	public CustomerDetails(int hidCustId) {
		this.customerId = hidCustId;
	}
	
	public CustomerDetails(String emailId) {
		super();
		this.emailId = emailId;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getAltPhoneNo() {
		return altPhoneNo;
	}
	public void setAltPhoneNo(String altPhoneNo) {
		this.altPhoneNo = altPhoneNo;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	public String getAltEmailId() {
		return altEmailId;
	}
	public void setAltEmailId(String altEmailId) {
		this.altEmailId = altEmailId;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public int getPincode() {
		return pincode;
	}
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Timestamp getDtStamp() {
		return dtStamp;
	}
	public void setDtStamp(Timestamp dtStamp) {
		this.dtStamp = dtStamp;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "CustomerDetails [customerId=" + customerId + ", customerName="
				+ customerName + ", firstName=" + firstName + ", lastName="
				+ lastName + ", emailId=" + emailId + ", password=" + password
				+ ", phoneNo=" + phoneNo + ", altPhoneNo=" + altPhoneNo
				+ ", securityQuestion=" + securityQuestion
				+ ", securityAnswer=" + securityAnswer + ", altEmailId="
				+ altEmailId + ", dob=" + dob + ", pincode=" + pincode
				+ ", state=" + state + ", country=" + country + ", dtStamp="
				+ dtStamp + ", address=" + address + ", status=" + status + "]";
	}
	
}