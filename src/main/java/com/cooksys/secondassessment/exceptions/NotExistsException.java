package com.cooksys.secondassessment.exceptions;

import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class NotExistsException extends Exception {

	public final int STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
	private String message;
	
	public NotExistsException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		return "NotExistsException [message=" + message + "]";
	}

}

