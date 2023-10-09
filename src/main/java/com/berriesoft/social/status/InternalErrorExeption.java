package com.berriesoft.social.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalErrorExeption extends RuntimeException
{
	private static final long serialVersionUID = -8639065045287573439L;

	public InternalErrorExeption(ErrorInfoList errorInfoList)
	{
		super(errorInfoList.toString());
	}
}
