package in.vamsoft.emscrm.utils;

import java.sql.Timestamp;

public class TicketDetails {
	
	private  int ticketId;
	private int customerId;
	private String eventName;
	private String eventDate;
	private String eventTime;
	private String location;
	private int noOfTicket;
	private String category;
	private double price;
	private double totalPrice;
	private String customerName;
	private Timestamp dtStamp;
	
	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	
	public TicketDetails(int ticketId, int customerId, String eventName,
			String eventDate, String eventTime, String location,
			int noOfTicket, String category, double price, double totalPrice,
			Timestamp dtStamp) {
		super();
		this.ticketId = ticketId;
		this.customerId = customerId;
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.location = location;
		this.noOfTicket = noOfTicket;
		this.category = category;
		this.price = price;
		this.totalPrice = totalPrice;
		this.dtStamp = dtStamp;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public TicketDetails() {
		// TODO Auto-generated constructor stub
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getNoOfTicket() {
		return noOfTicket;
	}

	public void setNoOfTicket(int noOfTicket) {
		this.noOfTicket = noOfTicket;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Timestamp getDtStamp() {
		return dtStamp;
	}

	public void setDtStamp(Timestamp dtStamp) {
		this.dtStamp = dtStamp;
	}

	@Override
	public String toString() {
		return "TicketDetails [ticketId=" + ticketId + ", customerId="
				+ customerId + ", eventName=" + eventName + ", eventDate="
				+ eventDate + ", eventTime=" + eventTime + ", location="
				+ location + ", noOfTicket=" + noOfTicket + ", category="
				+ category + ", price=" + price + ", totalPrice=" + totalPrice
				+ ", dtStamp=" + dtStamp + "]";
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	

}
