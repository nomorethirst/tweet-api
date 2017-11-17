package com.cooksys.tweetapi.exceptions;

import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AlreadyExistsException extends Exception {

    public final int STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
    public String responseMessage;

    public AlreadyExistsException(String message) {
        super();
        this.responseMessage = message;
    }

    @Override
    public String toString() {
        return "AlreadyExistsException [message=" + responseMessage + "]";
    }

}
