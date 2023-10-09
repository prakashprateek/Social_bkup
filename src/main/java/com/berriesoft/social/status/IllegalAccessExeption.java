package com.berriesoft.social.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.FORBIDDEN)
public class IllegalAccessExeption extends RuntimeException
{
	private static final long serialVersionUID = 1358638213467592927L;

	public IllegalAccessExeption(ErrorInfoList errorInfoList)
	{
		super(errorInfoList.toString());
	}
}
