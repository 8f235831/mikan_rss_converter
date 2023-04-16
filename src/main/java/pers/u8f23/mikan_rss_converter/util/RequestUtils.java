package pers.u8f23.mikan_rss_converter.util;

import lombok.extern.slf4j.Slf4j;
import pers.u8f23.mikan_rss_converter.config.Cons;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 8f23
 * @create 2023/4/16-20:09
 */
@Slf4j
public class RequestUtils
{

	private RequestUtils()
	{
		throw new UnsupportedOperationException();
	}

	public static String getFromUrlSingleTime(String path) throws Exception
	{
		URL url = new URL(path);
		byte[] bytes;
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(Cons.URL_CONNECT_TIME_OUT);
		connection.setReadTimeout(Cons.URL_READ_TIMEOUT);
		try (InputStream inputStream = connection.getInputStream())
		{
			bytes = inputStream.readAllBytes();
		}
		return new String(bytes);
	}

	public static String getFromUrl(String path, int forTimes) throws Exception
	{
		Exception finalException = null;
		for (int i = 1; i <= forTimes; i++)
		{
			try
			{
				String content = getFromUrlSingleTime(path);
				log.info("Succeed to request[{}/{}]: {}", i, forTimes, path);
				return content;

			}
			catch (Exception e)
			{
				finalException = e;
			}
		}
		throw new IOException("Connect retry time exceed[" + forTimes + "].",
			finalException);
	}

}
