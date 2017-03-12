package javabase.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by gary on 2017/3/3.
 */
public class RequestPractice {

    public static void detection(String stringUrl){

        HttpURLConnection urlConnection = null;
        System.setProperty("http.keepAlive", "false");
        try {
            URL url = new URL(stringUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("HEAD");
            int code = urlConnection.getResponseCode();
            System.out.println(code);
            urlConnection.getInputStream().close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

    }

    public static void main(String[] args){
        String url = "";
        detection(url);
    }
}
