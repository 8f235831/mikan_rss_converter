package pers.u8f23.mikan_rss_converter.entity;

import lombok.Getter;

@Getter
public class BaseResponse<T>
{
	private final T body;
	private final int code;
	private final String msg;

	public BaseResponse(T body, int code, String msg)
	{
		this.body = body;
		this.msg = msg;
		this.code = code;
	}
}
