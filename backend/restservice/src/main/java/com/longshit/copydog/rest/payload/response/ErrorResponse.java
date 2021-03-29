package com.longshit.copydog.rest.payload.response;

public class ErrorResponse {
    protected String errorMsg;
    protected String exception;

    public ErrorResponse(String errorMsg, String exception) {
        this.errorMsg = errorMsg;
        this.exception = exception;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
