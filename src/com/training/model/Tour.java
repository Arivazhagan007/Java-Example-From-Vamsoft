package com.training.model;

import java.time.LocalDate;

public class Tour {

	private int tourId;
	private String tourCode;
	private String tourName;
	private String boardingPlace;
	private String destinationPlace;
	private LocalDate startingDate;
	private LocalDate endingDate;
	private String placesCovered;
	private double amountPerPerson;
	public Tour() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Tour(String tourCode, String tourName, String boardingPlace, String destinationPlace, LocalDate startingDate,
			LocalDate endingDate, String placesCovered, double amountPerPerson) {
		super();
		this.tourCode = tourCode;
		this.tourName = tourName;
		this.boardingPlace = boardingPlace;
		this.destinationPlace = destinationPlace;
		this.startingDate = startingDate;
		this.endingDate = endingDate;
		this.placesCovered = placesCovered;
		this.amountPerPerson = amountPerPerson;
	}
	public Tour(int tourId, String tourCode, String tourName, String boardingPlace, String destinationPlace,
			LocalDate startingDate, LocalDate endingDate, String placesCovered, double amountPerPerson) {
		super();
		this.tourId = tourId;
		this.tourCode = tourCode;
		this.tourName = tourName;
		this.boardingPlace = boardingPlace;
		this.destinationPlace = destinationPlace;
		this.startingDate = startingDate;
		this.endingDate = endingDate;
		this.placesCovered = placesCovered;
		this.amountPerPerson = amountPerPerson;
	}
	public int getTourId() {
		return tourId;
	}
	public void setTourId(int tourId) {
		this.tourId = tourId;
	}
	public String getTourCode() {
		return tourCode;
	}
	public void setTourCode(String tourCode) {
		this.tourCode = tourCode;
	}
	public String getTourName() {
		return tourName;
	}
	public void setTourName(String tourName) {
		this.tourName = tourName;
	}
	public String getBoardingPlace() {
		return boardingPlace;
	}
	public void setBoardingPlace(String boardingPlace) {
		this.boardingPlace = boardingPlace;
	}
	public String getDestinationPlace() {
		return destinationPlace;
	}
	public void setDestinationPlace(String destinationPlace) {
		this.destinationPlace = destinationPlace;
	}
	public LocalDate getStartingDate() {
		return startingDate;
	}
	public void setStartingDate(LocalDate startingDate) {
		this.startingDate = startingDate;
	}
	public LocalDate getEndingDate() {
		return endingDate;
	}
	public void setEndingDate(LocalDate endingDate) {
		this.endingDate = endingDate;
	}
	public String getPlacesCovered() {
		return placesCovered;
	}
	public void setPlacesCovered(String placesCovered) {
		this.placesCovered = placesCovered;
	}
	public double getAmountPerPerson() {
		return amountPerPerson;
	}
	public void setAmountPerPerson(double amountPerPerson) {
		this.amountPerPerson = amountPerPerson;
	}
	@Override
	public String toString() {
		return "Tour [tourId=" + tourId + ", tourCode=" + tourCode + ", tourName=" + tourName + ", boardingPlace="
				+ boardingPlace + ", destinationPlace=" + destinationPlace + ", startingDate=" + startingDate
				+ ", endingDate=" + endingDate + ", placesCovered=" + placesCovered + ", amountPerPerson="
				+ amountPerPerson + "]";
	}
	
}
