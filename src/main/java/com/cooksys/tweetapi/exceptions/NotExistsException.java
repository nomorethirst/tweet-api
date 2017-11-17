package com.cooksys.tweetapi.exceptions;

import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class NotExistsException extends Exception {

    public final int STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
    public String responseMessage;

    public NotExistsException(String message) {
        super();
        this.responseMessage = message;
    }

    @Override
    public String toString() {
        return "NotExistsException [message=" + responseMessage + "]";
    }

}

