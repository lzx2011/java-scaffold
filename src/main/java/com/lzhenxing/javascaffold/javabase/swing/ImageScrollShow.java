package com.lzhenxing.javascaffold.javabase.swing;

import javax.swing.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ImageScrollShow <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/4/30
 */
public class ImageScrollShow extends JFrame {


    /**
     * 验证url的有效性
     * @param url
     */
    public static boolean isUrlValid(String url) {
        boolean flag = false;
        int counts = 0;
        if (url == null || url.length() <= 0) {
            return flag;
        }
        //重试3次
        while (counts < 3) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url)
                        .openConnection();
                int state = connection.getResponseCode();
                if (state == 200) {
                    // String realurl = connection.getURL().toString();
                    flag = true;
                }
                break;
            } catch (Exception ex) {
                counts++;
                continue;
            }
        }
        return flag;
    }

    public List<String> getImageUrls(){
        List<String> urlList = new ArrayList<>();
        String imageUrl = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3155280771,606419104&fm=23&gp=0.jpg";
        for(int i = 0; i < 7; i++){
            if(isUrlValid(imageUrl)){
                urlList.add(imageUrl);
            }
        }

        return urlList;
    }
}
