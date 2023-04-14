package pers.u8f23.mikan_rss_converter.config;

import pers.u8f23.mikan_rss_converter.entity.BaseResponse;

/**
 * @author 8f23
 * @create 2023/4/14-20:41
 */
public enum ResponseCode
{
	DEFAULT_SUCCESS
		{
			@Override
			public int code()
			{
				return 0;
			}
		},
	DEFAULT_FAILURE
		{
			@Override
			public int code()
			{
				return -1;
			}
		},
	ILLEGAL_REQUEST_PARAM_FAILURE
		{
			@Override
			public int code()
			{
				return -2;
			}

			@Override
			public String msg()
			{
				return "illegal request param!";
			}
		},
	NO_SUCH_ELEMENT_FAILURE
		{
			@Override
			public int code()
			{
				return -3;
			}

			@Override
			public String msg()
			{
				return "no such element!";
			}
		},
	DATA_ACCESS_FAILURE
		{
			@Override
			public int code()
			{
				return -4;
			}

			@Override
			public String msg()
			{
				return "failed to access data.";
			}
		};
	private static final String UNDEFINED_SUCCESS_MSG = "undefined success!";
	private static final String UNDEFINED_FAIL_MSG = "undefined fail!";

	/** 返回码。 */
	public abstract int code();

	/** 默认消息。 */
	public String msg()
	{
		return code() >= 0 ? UNDEFINED_SUCCESS_MSG : UNDEFINED_FAIL_MSG;
	}

	/** 默认带值返回体。 */
	public <T> BaseResponse<T> bodyResponse(T body)
	{
		return new BaseResponse<>(body, this.code(), this.msg());
	}

	/** 默认带值返回体。 */
	public <T> BaseResponse<T> bodyResponse(T body, String msg)
	{
		return new BaseResponse<>(body, this.code(), msg);
	}

	/** 默认空值返回体。 */
	public <T> BaseResponse<T> nullResponse()
	{
		return new BaseResponse<>(null, this.code(), this.msg());
	}

	/** 默认空值返回体。 */
	public <T> BaseResponse<T> nullResponse(String msg)
	{
		return new BaseResponse<>(null, this.code(), msg);
	}
}
