package com.lzhenxing.javascaffold.javabase.monitor.cpu;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ClassName: CpuHighFromIO <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/8/27
 */
public class CpuHighFromIO {

    public static void main(String[] args) throws Exception {
        final String tudou = "http://v.youku.com/v_playlist/f17170661o1p9.html";

        URL url = new URL(tudou);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        try {
            InputStream in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
            StringBuilder buf = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                if (StringUtils.isNotEmpty(buf.toString())) {
                    buf.append("\r\n");
                }
                buf.append(line);
            }
            //do something with 'buf'

        } finally {
            conn.disconnect();
        }

    }
}
