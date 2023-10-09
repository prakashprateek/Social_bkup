package com.berriesoft.social.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class UserNotFoundExeption extends RuntimeException
{
	private static final long serialVersionUID = 7874433125007477392L;
	
	public UserNotFoundExeption(ErrorInfoList errorInfoList)
	{
		super(errorInfoList.toString());
	}
	
}
