package com.berriesoft.social.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST)
public class UserNotCreatedExeption extends RuntimeException
{
	private static final long serialVersionUID = -8793978923020622058L;

	public UserNotCreatedExeption(ErrorInfoList errorInfoList)
	{
		super(errorInfoList.toString());
	}
}
