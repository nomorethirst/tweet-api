package com.cooksys.secondassessment.exceptions;

import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class InvalidRequestException extends Exception {

	public final int STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
	public String responseMessage;
	
	public InvalidRequestException(String message) {
		super();
		this.responseMessage = message;
	}

	@Override
	public String toString() {
		return "InvalidCredentialsException [message=" + responseMessage + "]";
	}

}
