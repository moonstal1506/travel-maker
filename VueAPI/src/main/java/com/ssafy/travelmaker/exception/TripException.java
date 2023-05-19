package com.ssafy.travelmaker.exception;

public class TripException extends RuntimeException{
	public TripException() {}
	public TripException(String msg) {
		super(msg);
	}
}
