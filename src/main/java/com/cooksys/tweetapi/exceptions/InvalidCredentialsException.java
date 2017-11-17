package com.cooksys.tweetapi.exceptions;

import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class InvalidCredentialsException extends Exception {

    public final int STATUS_CODE = HttpServletResponse.SC_FORBIDDEN;
    public String responseMessage;

    public InvalidCredentialsException(String message) {
        super();
        this.responseMessage = message;
    }

    @Override
    public String toString() {
        return "InvalidCredentialsException [message=" + responseMessage + "]";
    }

}
