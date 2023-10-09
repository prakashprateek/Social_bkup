package com.berriesoft.social.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST)
public class PostNotCreatedExeption extends Exception
{
	private static final long serialVersionUID = -4944932439314929449L;

	public PostNotCreatedExeption(ErrorInfoList errorInfoList)
	{
		super(errorInfoList.toString());
	}
}
