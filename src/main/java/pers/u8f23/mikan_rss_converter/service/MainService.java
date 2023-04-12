package pers.u8f23.mikan_rss_converter.service;

import pers.u8f23.mikan_rss_converter.config.Cons;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainService {
    public String getFromUrl(String path) throws Exception {
        URL url = new URL(path);
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(Cons.URL_CONNECT_TIME_OUT);
        connection.setReadTimeout(Cons.URL_READ_TIMEOUT);
        InputStream inputStream = connection.getInputStream();
        byte[] bytes = inputStream.readAllBytes();
        inputStream.close();
        return new String(bytes);
    }
}
