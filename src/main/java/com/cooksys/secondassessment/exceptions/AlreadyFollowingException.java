package com.cooksys.secondassessment.exceptions;

import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AlreadyFollowingException extends Exception {

	public final int STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
	public String responseMessage;
	
	public AlreadyFollowingException(String message) {
		super();
		this.responseMessage = message;
	}

	@Override
	public String toString() {
		return "AlreadyExistsException [message=" + responseMessage + "]";
	}

}
