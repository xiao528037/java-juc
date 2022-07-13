package com.xiao.juc._03_thread_synchronize.demo01;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-10 18:53:36
 * <p>
 * description：下载网页
 */
public class Downloader {
    public static List<String> download(String URL) throws IOException {
        List<String> list = new Vector<>();
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(URL).openConnection();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }

}
