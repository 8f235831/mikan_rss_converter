package pers.u8f23.mikan_rss_converter.config;

public class Cons
{
	public static final int URL_READ_TIMEOUT = 1000;
	public static final int URL_CONNECT_TIME_OUT = 1000;
	public static final int URL_CONNECT_MAX_RETRY_TIMES = 2;
	// 内容轮询任务执行周期为15秒。如果当前仍正在执行此任务，则任务自动后延至下一次周期。
	public static final long CONTENT_POLLING_PERIOD = 15 * 1000;
	// 内容过期时间为120秒。内容更新超过120秒后才会被再次更新。
	public static final long CONTENT_OUT_OF_DATE_PERIOD = 2 * 60 * 1000;
}
