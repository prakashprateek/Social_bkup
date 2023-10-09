package com.berriesoft.social.status;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "code", "field", "message" })
public class ErrorInfo {

	public static final int USER_NOT_FOUND_ERRORCODE = 10001;
	public static final String USER_NOT_FOUND_MESSAGE = "User not Found";
	public static final int USER_NOT_CREATED_ERRORCODE = 10002;
	public static final String USER_NOT_CREATED_MESSAGE = "Error - could not create user";
	public static final int POST_NOT_FOUND_ERRORCODE = 10003;
	public static final String POST_NOT_FOUND_MESSAGE = "No Posts found";
	public static final int POST_NOT_CREATED_ERRORCODE = 10004;
	public static final String POST_NOT_CREATED_MESSAGE = "Error - could not create post";
	public static final int ILLEGAL_ACCESS_CODE = 10005;
	public static final String ILLEGAL_ACCESS_MESSAGE = "Error - illegal access attempt";
	
    private int code;
    private String field;
    private String message;

    public ErrorInfo(int code, String field, String message)
	{
		super();
		this.code = code;
		this.field = field;
		this.message = message;
	}
    public ErrorInfo(int code, String message)
	{
		super();
		this.code = code;
		this.message = message;
	}

	public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
	@Override
	public String toString()
	{
		return "ErrorInfo [code=" + code + ", field=" + field + ", message=" + message + "]";
	}

}