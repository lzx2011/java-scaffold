package com.lzhenxing.javascaffold.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * ClassName: ImageCheckUtils <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/4/12
 */
public class ImageCheckUtils {

    private static Logger log = LoggerFactory.getLogger(ImageCheckUtils.class);

    /**
     * 图片的最小宽度，最小高度，最大宽度，最大高度
     */
    private static final int[] picSize = {300, 225, 1200,1200};

    private final static List<String> ALLOW_PIC_EXT = Arrays.asList("png", "jpg", "jpeg", "PNG", "JPG", "JPEG");

    private static int MAX_IMAGE_SIZE = 5;

    /**
     * check pic大小
     * @param image 图片对象
     * @return
     */
    public static boolean checkPicSize(BufferedImage image){
        if (image == null) {
            return false;
        }

        int width = image.getWidth();
        int height = image.getHeight();
        //        //去除大图片限制.
        if (width < picSize[0] || height < picSize[1]) {
            return false;
        }
        //		if (width < picSize[0] || height < picSize[1] || width > picSize[2] || height > picSize[3]) {
        //			return false;
        //		}
        return true;
    }

    public static boolean checkImageSize(String urlString){
        boolean flag = true;
        try{
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            int size = conn.getContentLength();
            if(size > MAX_IMAGE_SIZE * 1024 * 1024 || size < 0){
                flag = false;
            }
            conn.getInputStream().close();
        } catch (Exception e){
            log.error("checkImageSize", e);
        }

        return flag;
    }

    /**
     * 是否还要校验大小写
     * @param url
     * @return
     */
    public static boolean checkImageSuffix(String url) {

        boolean flag = false;
        if (StringUtils.isNotBlank(url) && url.contains(".")) {
            int index = url.lastIndexOf(".");
            String suffix = url.substring(index + 1);
            if (ALLOW_PIC_EXT.contains(suffix)) {
                return true;
            }
        }
        return flag;
    }

    public static void main(String[] args) throws Exception{

        //String url = "https://img.alicdn.com/imgextra/i4/2255708913/TB2NF_1cpXXXXb3XXXXXXXXXXXX_!!2255708913.jpg";
        String url = "http://img.alicdn.com/bao/uploaded/i1/TB1AQNWa7CWBuNjy0FaqkJUlXXa_020918.jpg";
        System.out.println(checkImageSuffix(url));
        System.out.println(checkImageSize(url));
        InputStream murl = new URL(url).openStream();
        System.out.println(checkPicSize(ImageIO.read(murl)));
        murl.close();
    }
}
