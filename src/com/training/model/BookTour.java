package com.training.model;

public class BookTour {

	private int bookTourId;
	private int TourId;
	private int customerId;
	private int countOfMembers;
	private double totalAmount;
	public BookTour() {
		super();
	
	}
	
	public BookTour(int tourId, int customerId, int countOfMembers, double totalAmount) {
		super();
		TourId = tourId;
		this.customerId = customerId;
		this.countOfMembers = countOfMembers;
		this.totalAmount = totalAmount;
	}

	public BookTour(int bookTourId, int tourId, int customerId, int countOfMembers, double totalAmount) {
		super();
		this.bookTourId = bookTourId;
		TourId = tourId;
		this.customerId = customerId;
		this.countOfMembers = countOfMembers;
		this.totalAmount = totalAmount;
	}
	public int getBookTourId() {
		return bookTourId;
	}
	public void setBookTourId(int bookTourId) {
		this.bookTourId = bookTourId;
	}
	public int getTourId() {
		return TourId;
	}
	public void setTourId(int tourId) {
		TourId = tourId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getCountOfMembers() {
		return countOfMembers;
	}
	public void setCountOfMembers(int countOfMembers) {
		this.countOfMembers = countOfMembers;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	@Override
	public String toString() {
		return "BookTour [bookTourId=" + bookTourId + ", TourId=" + TourId + ", customerId=" + customerId
				+ ", countOfMembers=" + countOfMembers + ", totalAmount=" + totalAmount + "]";
	}
}
