package com.glwm.common;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FileUtils {
    /**
     * 下载文件到指定目录
     * @param dowUrl:http地址
     *
     * */
    public static String download(String dowUrl){
        try {

            String dowPath = "F:\\tackoutHtml\\backend\\images\\merchantsLogo";

            //log.info("下载地址是："+dowUrl+",存储地址是："+dowPath);
            URL url = new URL(dowUrl);

            URLConnection urlConnection = url.openConnection();

            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;// http的连接类

            //String contentType = httpURLConnection.getContentType();//请求类型,可用来过滤请求，

            httpURLConnection.setConnectTimeout(1000*5);//设置超时

            httpURLConnection.setRequestMethod("POST");//设置请求方式，默认是GET

            httpURLConnection.setRequestProperty("Charset", "UTF-8");// 设置字符编码

            httpURLConnection.connect();// 打开连接

            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

            String path = dowPath;// 指定存放位置
            File filed = new File(path);

            OutputStream out = new FileOutputStream(filed);
            int size = 0;

            byte[] b = new byte[2048];
            //把输入流的文件读取到字节数据b中，然后输出到指定目录的文件
            while ((size = bin.read(b)) != -1) {
                out.write(b, 0, size);
            }
            // 关闭资源
            bin.close();
            out.close();
            return "200";
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "500";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "400";
        }
    }

}
