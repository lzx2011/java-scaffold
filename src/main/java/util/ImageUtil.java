package util;//package com.liu.util;
//
//import java.awt.AlphaComposite;
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.image.BufferedImage;
//import java.awt.image.DataBufferInt;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import javax.imageio.ImageIO;
//import net.coobird.thumbnailator.Thumbnails;
//import wafly.tool.util.file.FileUtil;
//
//public class ImageUtil {
//
//	/** 从指定路径的图片文件中获取图片对象，如果失败，则返回一个1像素的空图片对象；从不返回null */
//	public static Image getImage(String path) {
//		File imgFile = new File(path);
//		return getImage(imgFile);
//	}
//
//	/** 从图片文件中获取图片对象，如果失败，则返回一个1像素的空图片对象；从不返回null */
//	public static Image getImage(File imgFile) {
//		if (!imgFile.exists() || !imgFile.isFile())
//			return new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
//		try {
//			return ImageIO.read(imgFile);
//		} catch (Exception e) {
//			return new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
//		}
//	}
//
//	/** 从指定路径的图片文件中获取指定尺寸的图片对象，如果失败，则返回一个1像素的空图片对象，指定的尺寸不得小于1；从不返回null */
//	public static Image getImage(String path, int width, int height) {
//		File imgFile = new File(path);
//		return getImage(imgFile, width, height);
//	}
//
//	/** 从图片文件中获取指定尺寸的图片对象，如果失败，则返回一个1像素的空图片对象，指定的尺寸不得小于1；从不返回null */
//	public static Image getImage(File imgFile, int width, int height) {
//		BufferedImage img = null;
//		if (!imgFile.exists() || !imgFile.isFile())
//			img = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
//		try {
//			img = ImageIO.read(imgFile);
//		} catch (Exception e) {
//			img = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
//		}
//		if (width < 1)
//			width = 1;
//		if (height < 1)
//			height = 1;
//		BufferedImage thumb = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
//		thumb.getGraphics().drawImage(img, 0, 0, width, height, null);
//		return thumb;
//	}
//
//	public static Image createBlankImage(int width, int height) {
//		return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
//	}
//
//	/** 在指定的图片上绘制另一幅图片 */
//	public static void drawImage(Image baseImage, Image drawSource, int x, int y) {
//		Graphics g = baseImage.getGraphics();
//		g.drawImage(drawSource, x, y, null);
//	}
//
//	/** 转换为灰度图片（黑白模式） */
//	public static void convertToGray(BufferedImage image) {
//		int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
//		for (int i = 0; i < pixels.length; i++) {
//			if (pixels[i] == 0)
//				continue; // 对透明像素不做处理
//			int value = (int) (0.299 * ((pixels[i] >> 16) & 0xff) + 0.587 * ((pixels[i] >> 8) & 0xff)
//					+ 0.114 * (pixels[i] & 0xff));
//			pixels[i] = (255 << 24) + (value << 16) + (value << 8) + value;
//		}
//	}
//
//	/** 将图片颜色反相（底片模式） */
//	public static void convertToXor(BufferedImage image) {
//		int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
//		for (int i = 0; i < pixels.length; i++) {
//			if (pixels[i] == 0)
//				continue; // 对透明像素不做处理
//			pixels[i] = (255 << 24) + (((pixels[i] >> 16) & 0xff) ^ 0xff << 16)
//					+ (((pixels[i] >> 8) & 0xff) ^ 0xff << 8) + (pixels[i] & 0xff) ^ 0xff;
//		}
//	}
//
//	/**
//	 * 在图片上方覆盖一层颜色
//	 * @param alpha 覆盖的颜色的透明度
//	 */
//	public static void fillColor(Image image, Color color, float alpha) {
//		Graphics2D g = (Graphics2D) image.getGraphics();
//		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
//		Color oriColor = g.getColor();
//		g.setColor(color);
//		g.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
//		g.setColor(oriColor);
//	}
//
//	/**
//	 * 获取图片对象的缩略图
//	 * @param size 限定缩略图的最大尺寸，如果图片本身小于这个尺寸，则返回原图片
//	 * @return 成功创建缩略图时，返回BufferedImage格式的缩略图，否则返回原来格式的对象。
//	 */
//	public static BufferedImage getThumbnail(BufferedImage img, int size) {
//		int drawWidth = img.getWidth(null), drawHeight = img.getHeight(null);
//		if (drawWidth > size || drawHeight > size) {
//			if (drawWidth > drawHeight) {
//				drawHeight = size * drawHeight / drawWidth;
//				drawWidth = size;
//			} else {
//				drawWidth = size * drawWidth / drawHeight;
//				drawHeight = size;
//			}
//			// BufferedImage thumb = new BufferedImage(drawWidth, drawHeight, BufferedImage.TYPE_3BYTE_BGR);
//			// thumb.getGraphics().drawImage(img, 0, 0, drawWidth, drawHeight, null);
//			// return thumb;
//			try {
//				return Thumbnails.of(img).size(drawWidth, drawHeight).asBufferedImage();
//			} catch (Exception e) {
//				return new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
//			}
//		} else {
//			return img;
//		}
//	}
//
//	/** 将图片对象保存到指定路径中，并返回保存结果 */
//	public static boolean saveToFile(BufferedImage img, String filePath, ImageType type) {
//		File imgFile = new File(filePath);
//		return saveToFile(img, imgFile, type);
//	}
//
//	/** 将图片对象保存到指定路径中，并返回保存结果 */
//	public static boolean saveToFile(BufferedImage img, File imgFile, ImageType type) {
//		if (imgFile.exists() && !new FileUtil().delete(imgFile.getAbsolutePath()))
//			return false;
//		try {
//			ImageIO.write(img, type.toString(), imgFile);
//		} catch (Exception e) {
//			return false;
//		}
//		return true;
//	}
//
//	public static byte[] getImageData(File imageFile, ImageType type) {
//		try {
//			BufferedImage img = ImageIO.read(imageFile);
//			ByteArrayOutputStream buf = new ByteArrayOutputStream((int) imageFile.length());
//			ImageIO.write(img, type.toString(), buf);
//			return buf.toByteArray();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	public static byte[] getImageData(BufferedImage img, ImageType type) {
//		try {
//			ByteArrayOutputStream buf = new ByteArrayOutputStream();
//			ImageIO.write(img, type.toString(), buf);
//			return buf.toByteArray();
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	public enum ImageType {
//		JPG, PNG, BMP
//	}
//
//	public static void main(String[] a) {
//		System.out.println(System.currentTimeMillis());
//		String filePath = "C:\\Users\\Administrator\\Desktop\\img120.jpg";
//		ImageUtil.saveToFile((BufferedImage) ImageUtil.getThumbnail((BufferedImage) ImageUtil.getImage(filePath), 64),
//				filePath, ImageType.PNG);
//		System.out.println(System.currentTimeMillis());
//
//	}
//}
