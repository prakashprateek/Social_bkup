package com.berriesoft.social.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class PostNotFoundExeption extends RuntimeException
{
	private static final long serialVersionUID = -8416443145409677721L;

	public PostNotFoundExeption(ErrorInfoList errorInfoList)
	{
		super(errorInfoList.toString());
	}
}


