package in.vamsoft.emscrm.utils;

import java.sql.Timestamp;




public class UserBean {

	private int customer_id;
	private String customer_name;
	private String email_id;
	private String phone_no;
	private Timestamp dtstamp;
	
	private int issue_id;
	//private int customer_id;
	private String issue_subject;
	private String issue_description;
	//private Timestamp dtstamp;
	
	private int ticket_id;
	//private int customer_id;
	private String event_date;
	private String event_time;
	private String location;
	private int number_of_ticket;
	private String category;
	private int price;
	//private Timestamp dtstamp;
	
	public int getCustomer_id() {
		return customer_id;
	}
	public int getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public Timestamp getDtstamp() {
		return dtstamp;
	}
	public void setDtstamp(Timestamp dtstamp) {
		this.dtstamp = dtstamp;
	}
	public int getIssue_id() {
		return issue_id;
	}
	public void setIssue_id(int issue_id) {
		this.issue_id = issue_id;
	}
	public String getIssue_subject() {
		return issue_subject;
	}
	public void setIssue_subject(String issue_subject) {
		this.issue_subject = issue_subject;
	}
	public String getIssue_description() {
		return issue_description;
	}
	public void setIssue_description(String issue_description) {
		this.issue_description = issue_description;
	}
	public String getEvent_date() {
		return event_date;
	}
	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}
	public String getEvent_time() {
		return event_time;
	}
	public void setEvent_time(String event_time) {
		this.event_time = event_time;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getNumber_of_ticket() {
		return number_of_ticket;
	}
	public void setNumber_of_ticket(int number_of_ticket) {
		this.number_of_ticket = number_of_ticket;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public UserBean(int customer_id, String customer_name, String email_id,
			String phone_no, Timestamp dtstamp, int issue_id,
			String issue_subject, String issue_description, String event_date,
			String event_time, String location, int number_of_ticket,
			String category, int price) {
		super();
		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.email_id = email_id;
		this.phone_no = phone_no;
		this.dtstamp = dtstamp;
		this.issue_id = issue_id;
		this.issue_subject = issue_subject;
		this.issue_description = issue_description;
		this.event_date = event_date;
		this.event_time = event_time;
		this.location = location;
		this.number_of_ticket = number_of_ticket;
		this.category = category;
		this.price = price;
	}
	public UserBean() {
		super();
	}
	
	
	
}