package com.lzhenxing.javascaffold.javabase.swing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    public ImageScrollShow(){
        setSize(800, 600);
        Container c = getContentPane();
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize(); // 获得显示器大小
        Dimension frameSize = getSize();
        if (frameSize.width > displaySize.width)
            frameSize.width = displaySize.width; // 窗口的宽度不能大于显示器的宽度
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation((displaySize.width - frameSize.width) / 2, (displaySize.height - frameSize.height) / 2); // 设置窗口居中显示器显示
        JPanel jp = new JPanel(new GridLayout(3,1,1,1));
        //jp.setLayout(new GridLayout(1,3));
        List<String> urls = getImageUrls();
        if(urls != null && urls.size() > 0){
            for(String url : urls){
                JLabel cl = new JLabel();
                cl.setIcon(new ImageIcon(getScaleImage(url, 257, 326, false)));
                jp.add(cl);
            }
        }
//        jp.setPreferredSize(new Dimension(600, 400));
        JScrollPane jsp = new JScrollPane(jp,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //jsp.revalidate();//重新显示JScrollPane形状
        c.add(jsp);

//		c.add(jp);
        setTitle("图片列表");
        setVisible(true);

    }


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

    public static List<String> getImageUrls(){
        List<String> urlList = new ArrayList<>();
        String imageUrl = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3155280771,606419104&fm=23&gp=0.jpg";
        for(int i = 0; i < 10; i++){
            if(isUrlValid(imageUrl)){
                urlList.add(imageUrl);
            }
        }

        return urlList;
    }

    /**
     * 缩放图片
     * @param urlStr
     * @param height
     * @param width
     * @param filler
     * @return
     */
    public static byte[] getScaleImage(String urlStr, int height, int width, boolean filler) {
        ByteArrayOutputStream baos = null;
        try {
            double ratio; // 缩放比例
            baos = new ByteArrayOutputStream();
            URL url = new URL(urlStr);
            BufferedImage bi = ImageIO.read(url);
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (filler) {// 补白
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null),
                            itemp.getHeight(null), Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null),
                            itemp.getHeight(null), Color.white, null);
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, "JPEG", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }



    public static void main(String[] args){
        new ImageScrollShow();

    }
}
