package in.vamsoft.emscrm.utils;

import java.util.Date;

public class EventDetails {
	
	private int eventId;
	private String eventName;
	private String eventDate;
	private String eventTime;
	private String category;
	private String location;
	private String organisedBy;
	private double price;
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getOrganisedBy() {
		return organisedBy;
	}
	public void setOrganisedBy(String organisedBy) {
		this.organisedBy = organisedBy;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "EventDetails [eventId=" + eventId + ", eventName=" + eventName
				+ ", eventDate=" + eventDate + ", eventTime=" + eventTime
				+ ", category=" + category + ", location=" + location
				+ ", organisedBy=" + organisedBy + ", price=" + price + "]";
	}
	

	
}
