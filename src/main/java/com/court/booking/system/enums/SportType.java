package com.court.booking.system.enums;

public enum SportType {

	BADMINTON("BADMINTON"),
	TENNIS("TENNIS"),
	FOOTBALL("FOOTBALL");

	private String sport;

	SportType(String sport) {
		this.sport = sport;
	}
}