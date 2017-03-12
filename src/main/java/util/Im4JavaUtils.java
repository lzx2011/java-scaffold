package util;

import org.im4java.core.*;
import org.im4java.process.ArrayListOutputConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gary on 2017/1/1.
 */
public class Im4JavaUtils {

    private static Logger logger = LoggerFactory.getLogger(Im4JavaUtils.class);

    // 图片质量
    public static final String IMAGE_QUALITY = "quality";
    // 图片高度
    public static final String IMAGE_HEIGHT = "height";
    // 图片宽度
    public static final String IMAGE_WIDTH = "width";
    // 图片格式
    public static final String IMAGE_SUFFIX = "suffix";
    // 图片大小
    public static final String IMAGE_SIZE = "size";
    // 图片路径
    public static final String IMAGE_PATH = "path";

    /**
     * 是否使用 GraphicsMagick
     */
    private static final boolean IS_USE_GRAPHICS_MAGICK = true;

    /**
     * ImageMagick安装路径
     */
    private static final String IMAGE_MAGICK_PATH = "D:\\software\\ImageMagick-6.2.7-Q8";

    /**
     * GraphicsMagick 安装目录
     */
    private static final String GRAPHICS_MAGICK_PATH = "/usr/local/bin";

    /**
     * 水印图片路径
     */
    private static final String watermarkImagePath = "/Users/gary/Documents/Job/ImageProcessTool/Im4java/Linux_logo.jpg";
    /**
     * 水印图片
     */
    private static Image watermarkImage = null;
    static {
        try {
            watermarkImage = ImageIO.read(new File(watermarkImagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 命令类型
     *
     * @author hailin0@yeah.net
     * @createDate 2016年6月5日
     */
    private enum CommandType {
        convert("转换处理"), identify("图片信息"), compositecmd("图片合成");
        private String name;

        CommandType(String name) {
            this.name = name;
        }
    }

    /**
     * 获得图片文件大小
     */
    public int getSize(String imagePath) {
        int size = 0;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(imagePath);
            size = inputStream.available();
            inputStream.close();
            inputStream = null;
        } catch (FileNotFoundException e) {
            size = 0;
            System.out.println("文件未找到!");
        } catch (IOException e) {
            size = 0;
            System.out.println("读取文件大小错误!");
        } finally {
            // 可能异常为关闭输入流,所以需要关闭输入流
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println("关闭文件读入流异常");
                }
                inputStream = null;

            }
        }
        return size;
    }

    /**
     * 图片信息
     *
     * @param imagePath
     * @return
     */
    public static String getImageInfo1(String imagePath) {
        String line = null;
        try {
            IMOperation op = new IMOperation();
            op.format("width:%w,height:%h,path:%d%f,quality:%Q,size:%b%[EXIF:DateTimeOriginal]");
            op.addImage();
            IdentifyCmd identifyCmd = new IdentifyCmd(true);
            ArrayListOutputConsumer output = new ArrayListOutputConsumer();
            identifyCmd.setOutputConsumer(output);
            identifyCmd.run(op, imagePath);
            ArrayList<String> cmdOutput = output.getOutput();
            assert cmdOutput.size() == 1;
            line = cmdOutput.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }

    /**
     * 查询图片的基本信息:格式,质量，宽度，高度
     * <p>
     * gm identify -format %w,%h,%d/%f,%Q,%b,%e /Users/gary/Documents/999999999/10005582/1.jpg
     * <p>
     * <pre>
     *    %b   file size of image read in
     *    %c   comment property
     *    %d   directory component of path
     *    %e   filename extension or suffix
     *    %f   filename (including suffix)
     *    %g   layer canvas page geometry   ( = %Wx%H%X%Y )
     *    %h   current image height in pixels
     *    %i   image filename (note: becomes output filename for "info:")
     *    %k   number of unique colors
     *    %l   label property
     *    %m   image file format (file magic)
     *    %n   exact number of images in current image sequence
     *    %o   output filename  (used for delegates)
     *    %p   index of image in current image list
     *    %q   quantum depth (compile-time constant)
     *    %r   image class and colorspace
     *    %s   scene number (from input unless re-assigned)
     *    %t   filename without directory or extension (suffix)
     *    %u   unique temporary filename (used for delegates)
     *    %w   current width in pixels
     *    %x   x resolution (density)
     *    %y   y resolution (density)
     *    %z   image depth (as read in unless modified, image save depth)
     *    %A   image transparency channel enabled (true/false)
     *    %C   image compression type
     *    %D   image dispose method
     *    %G   image size ( = %wx%h )
     *    %H   page (canvas) height
     *    %M   Magick filename (original file exactly as given,  including read mods)
     *    %O   page (canvas) offset ( = %X%Y )
     *    %P   page (canvas) size ( = %Wx%H )
     *    %Q   image compression quality ( 0 = default )
     *    %S   ?? scenes ??
     *    %T   image time delay
     *    %W   page (canvas) width
     *    %X   page (canvas) x offset (including sign)
     *    %Y   page (canvas) y offset (including sign)
     *    %Z   unique filename (used for delegates)
     *    %@   bounding box
     *    %#   signature
     *    %%   a percent sign
     *    \n   newline
     *    \r   carriage return
     * </pre>
     *
     * @param imagePath
     * @return
     */
    public static Map<String, String> getImageInfo(String imagePath) {
        long startTime = System.currentTimeMillis();
        Map<String, String> imageInfo = new HashMap<>();
        try {
            IMOperation op = new IMOperation();
            op.format("%w,%h,%d/%f,%Q,%b,%e");
            op.addImage();
            ImageCommand identifyCmd = getImageCommand(CommandType.identify);
            ArrayListOutputConsumer output = new ArrayListOutputConsumer();
            identifyCmd.setOutputConsumer(output);
            identifyCmd.run(op, imagePath);
            ArrayList<String> cmdOutput = output.getOutput();
            String[] result = cmdOutput.get(0).split(",");
            if (result.length == 6) {
                imageInfo.put(IMAGE_WIDTH, result[0]);
                imageInfo.put(IMAGE_HEIGHT, result[1]);
                imageInfo.put(IMAGE_PATH, result[2]);
                imageInfo.put(IMAGE_QUALITY, result[3]);
                imageInfo.put(IMAGE_SIZE, result[4]);
                imageInfo.put(IMAGE_SUFFIX, result[5]);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            logger.error("图片工具获取图片基本信息异常" + e.getMessage(), e);
        }
        long endTime = System.currentTimeMillis();
        // logger.info("take time: " + (endTime - startTime));
        return imageInfo;
    }

    /**
     * 裁剪图片
     *
     * @param imagePath 源图片路径
     * @param newPath   处理后图片路径
     * @param x         起始X坐标
     * @param y         起始Y坐标
     * @param width     裁剪宽度
     * @param height    裁剪高度
     * @return 返回true说明裁剪成功, 否则失败
     */
    public static boolean cutImage(String imagePath, String newPath, int x, int y, int width, int height) {
        boolean flag = false;
        try {
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            /** width：裁剪的宽度 * height：裁剪的高度 * x：裁剪的横坐标 * y：裁剪纵坐标 */
            op.crop(width, height, x, y);
            op.addImage(newPath);
            ConvertCmd convert = new ConvertCmd(true);
            convert.run(op);
            flag = true;
        } catch (IOException e) {
            System.out.println("文件读取错误!");
            flag = false;
        } catch (InterruptedException e) {
            flag = false;
        } catch (IM4JavaException e) {
            flag = false;
        } finally {

        }
        return flag;
    }

    /**
     * 根据尺寸缩放图片[等比例缩放:参数height为null,按宽度缩放比例缩放;参数width为null,按高度缩放比例缩放]
     *
     * @param imagePath 源图片路径
     * @param newPath   处理后图片路径
     * @param width     缩放后的图片宽度
     * @param height    缩放后的图片高度
     * @return 返回true说明缩放成功, 否则失败
     */
    public static boolean zoomImage(String imagePath, String newPath, Integer width, Integer height) {

        boolean flag;
        try {
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            if (width == null) {// 根据高度缩放图片
                op.resize(null, height);
            } else if (height == null) {// 根据宽度缩放图片
                op.resize(width);
            } else {
                op.resize(width, height);
            }
            op.addImage(newPath);
            ConvertCmd convert = new ConvertCmd(true);
            convert.run(op);
            flag = true;
        } catch (IOException e) {
            System.out.println("文件读取错误!");
            flag = false;
        } catch (InterruptedException e) {
            flag = false;
        } catch (IM4JavaException e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 图片旋转(顺时针旋转) 拼装命令示例: gm convert -rotate 90 /apps/watch.jpg /apps/watch_compress.jpg
     *
     * @param imagePath 源图片路径
     * @param newPath   处理后图片路径
     * @param degree    旋转角度
     */
    public static boolean rotate(String imagePath, String newPath, double degree) {
        boolean flag;
        try {
            // 1.将角度转换到0-360度之间
            degree = degree % 360;
            if (degree <= 0) {
                degree = 360 + degree;
            }
            IMOperation op = new IMOperation();
            op.rotate(degree);
            op.addImage(imagePath);
            op.addImage(newPath);
            ConvertCmd cmd = new ConvertCmd(true);
            cmd.run(op);
            flag = true;
        } catch (Exception e) {
            flag = false;
            System.out.println("图片旋转失败!");
        }
        return flag;
    }

    /**
     * 给图片加水印
     *
     * @param srcPath 源图片路径
     */
    public static void addImgText(String srcPath, String content, String desc) throws Exception {
        IMOperation op = new IMOperation();
        // op.font("/Library/Fonts/Yuanti.ttc").gravity("southeast").pointsize(18).fill("#BCBFC8").draw("text 10,15 '" +
        // content + "'").quality(90.0);
        op.gravity("southeast").pointsize(18).fill("#FFFFFF").draw("text 10,15 '" + content + "'").quality(90.0);
        op.addImage();
        op.addImage();
        ConvertCmd cmd = new ConvertCmd(true);
        cmd.setAsyncMode(true);
        cmd.setSearchPath("/usr/local/bin");// 如果是windows则需要设置convert路径
        try {
            cmd.run(op, srcPath, desc);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IM4JavaException e) {

            e.printStackTrace();
        }
    }

    /**
     * 文字水印
     *
     * @param srcImagePath  源图片路径
     * @param destImagePath 目标图片路径
     * @param content       文字内容（不支持汉字）
     * @throws Exception
     */
    public static void addTextWatermark(String srcImagePath, String destImagePath, String content) throws Exception {
        IMOperation op = new IMOperation();
        // op.font("微软雅黑");
         op.font("ArialBold");
        // 文字方位-东南
        op.gravity("southeast");
        // 文字信息
        // op.pointsize(18).fill("#BCBFC8").draw("text 10,10 " + content);
        op.pointsize(60).fill("#F2F2F2").draw("text 10,10 " + content);
        //设置透明度 不会起作用
        //op.dissolve(20);
        // 原图
        op.addImage(srcImagePath);
        // 目标
        op.addImage(destImagePath);
        ImageCommand cmd = getImageCommand(CommandType.convert);
        cmd.run(op);
    }

    /**
     * 图片水印
     *
     * @param srcImagePath  源图片路径
     * @param destImagePath 目标图片路径
     * @param dissolve      透明度（0-100）
     * @throws Exception
     */
    public static void addImgWatermark(String srcImagePath, String destImagePath, Integer dissolve) throws Exception {
        // 原始图片信息
        BufferedImage buffimg = ImageIO.read(new File(srcImagePath));
        int w = buffimg.getWidth();
        int h = buffimg.getHeight();
        IMOperation op = new IMOperation();
        // 水印图片位置
        op.geometry(watermarkImage.getWidth(null), watermarkImage.getHeight(null),
                w - watermarkImage.getWidth(null) - 10, h - watermarkImage.getHeight(null) - 10);
        // 水印透明度
        op.dissolve(dissolve);
        // 水印
        op.addImage(watermarkImagePath);
        // 原图
        op.addImage(srcImagePath);
        // 目标
        op.addImage(destImagePath);
        ImageCommand cmd = getImageCommand(CommandType.compositecmd);
        cmd.run(op);
    }

    /**
     * 等比缩放图片（如果width为空，则按height缩放; 如果height为空，则按width缩放）
     *
     * @param srcImagePath  源图片路径
     * @param destImagePath 目标图片路径
     * @param width         缩放后的宽度
     * @param height        缩放后的高度
     * @throws Exception
     */
    public static void scaleResize(String srcImagePath, String destImagePath, Integer width, Integer height)
            throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcImagePath);
        op.sample(width, height);
        op.addImage(createDirectory(destImagePath));
        ImageCommand cmd = getImageCommand(CommandType.convert);
        cmd.run(op);
    }

    /**
     * 获取 ImageCommand
     *
     * @param command 命令类型
     * @return
     */
    private static ImageCommand getImageCommand(CommandType command) {
        ImageCommand cmd = null;
        switch (command) {
            case convert:
                cmd = new ConvertCmd(IS_USE_GRAPHICS_MAGICK);
                break;
            case identify:
                cmd = new IdentifyCmd(IS_USE_GRAPHICS_MAGICK);
                break;
            case compositecmd:
                cmd = new CompositeCmd(IS_USE_GRAPHICS_MAGICK);
                break;
        }
        if (cmd != null && System.getProperty("os.name").toLowerCase().indexOf("windows") == -1) {
            cmd.setSearchPath(IS_USE_GRAPHICS_MAGICK ? GRAPHICS_MAGICK_PATH : IMAGE_MAGICK_PATH);
        }
        return cmd;
    }

    /**
     * 去除Exif信息，可减小文件大小,会减小图片质量,慎用
     *
     * @param srcImagePath  源图片路径
     * @param destImagePath 目标图片路径
     * @throws Exception
     */
    public static void removeProfile(String srcImagePath, String destImagePath) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcImagePath);
        op.profile("*");
        // op.addImage(createDirectory(destImagePath));
        op.addImage(destImagePath);
        ImageCommand cmd = getImageCommand(CommandType.convert);
        cmd.run(op);
    }

    /**
     * 创建目录
     *
     * @param path
     * @return path
     */
    private static String createDirectory(String path) {
        File file = new File(path);
        if (!file.exists())
            file.getParentFile().mkdirs();
        return path;
    }

    /**
     * 图片压缩
     * <p>
     * 拼装命令示例: gm convert -quality 80 /apps/watch.jpg /apps/watch_compress.jpg
     *
     * @param srcImagePath
     * @param destImagePath
     * @param quality
     * @throws Exception
     */
    public static void compressImage(String srcImagePath, String destImagePath, double quality) throws Exception {
        IMOperation op = new IMOperation();
        op.quality(quality);
        op.addImage();
        op.addImage();
        ImageCommand cmd = getImageCommand(CommandType.convert);
        cmd.run(op, srcImagePath, destImagePath);
    }

    public static void main(String[] args) throws Exception {
        // 加水印
        String src = "/Users/xxx/Documents/temp/test/ban.jpg"; // 需要加水印的源图片
        String desc = "/Users/xxx/Documents/temp/test/new/abc"; // 生成的水印图片的路径
        // String content = "中文";
        // for (int i = 0; i < 10; i++) {
        // addImgText(src, content + i, desc + i + ".jpg");
        // }

        String imagePath = "/Users/gary/Documents/999999999/10005582/1.jpg";
        // String imagePath = "/Users/gary/Documents/999999999/10005582/compress/desk.jpg";
        logger.info("log test");
        // String imagePath = "/Users/gary/Documents/999999999/10005582/compress/downjacket.jpg";
        // String imagePath = "/apps/watch.jpg";
        // System.out.println(getImageInfo(imagePath).toString());
        // compress
        // String destPath = "/Users/gary/Documents/999999999/10005582/compress/desk_compress_85.jpg";
        // compressImage(imagePath, destPath, 85d);
        // 不要去除冗余信息,质量可能会严重变低的
        // String destPath = "/Users/gary/Documents/999999999/10005582/compress/downjacket_remove.jpg";
        // removeProfile(imagePath, destPath);
        // System.out.println(getImageInfo(destPath).toString());



        // 旋转测试
        String sourcePath = "/Users/gary/Documents/Job/ImageProcessTool/Im4java/1.jpg";
        // String rotatPath = "/Users/gary/Documents/Job/ImageProcessTool/Im4java/1_rotat_90.jpg";
        // rotate(sourcePath, rotatPath, 90d);
        // 缩放测试
        // String zoomPath = "/Users/gary/Documents/Job/ImageProcessTool/Im4java/1_zoom_500_500.jpg";
        // zoomImage(sourcePath, zoomPath, 500, 500);
        // 裁剪测试
        // String cutImagePath = "/Users/gary/Documents/Job/ImageProcessTool/Im4java/1_cut_100_100_500_500.jpg";
        // cutImage(sourcePath, cutImagePath, 100, 100, 500, 500);
        // 加图片水印
        String addImageWatermarkPath = "/Users/gary/Documents/Job/ImageProcessTool/Im4java/1_image_watermark.jpg";
        addImgWatermark(sourcePath, addImageWatermarkPath, 20);
        // 加文字水印
        String addTextWatermarkPath = "/Users/gary/Documents/Job/ImageProcessTool/Im4java/1_text_watermark.jpg";
         //addTextWatermark(sourcePath, addTextWatermarkPath, "gary");
        //addTextWatermark(sourcePath, addTextWatermarkPath, "中文"); // 现在中文会乱码
        // addImgText(sourcePath, "gary", "my test");


        //分割和组合略了 参考:https://github.com/hailin0/im4java-util/blob/master/src/main/java/com/hlin/im4java/util/ImageUtil.java
    }
}

