package com.autoserve.abc.service.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 工具类，生成验证码图片
 * @author DS
 *
 * 2015年4月28日上午9:30:47
 */
public class SecurityImage {
	
	/**
	 * 生成验证码图片
	 * @param securityCode 验证码字符
	 * @return BufferedImage 图片 
	 */
	public static  BufferedImage createImage(String securityCode){
		
		//验证码长度
		int codeLength = securityCode.length();
		//字体大小
		int fSize = 27;
		int fWidth = fSize + 1;
		//图片宽度
		int width = codeLength * fWidth;
		//图片高度
		int height = fSize + 10;
		
		//图片
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		
		//设置背景色
		g.setColor(Color.WHITE);
		//填充背景
		g.fillRect(0, 0, width, height);
		
		//设置边框颜色
		g.setColor(Color.LIGHT_GRAY);
		//边框字体样式
		g.setFont(new Font("Arial", Font.BOLD, height - 2));
		//绘制边框
		g.drawRect(0, 0, width - 1, height - 1);
		
		//绘制噪点
		Random rand = new Random();
		//设置噪点颜色
//		g.setColor(Color.GRAY);
		for (int i = 0; i < codeLength * 10; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			//绘制1*1大小的矩形
			g.setColor(new Color(rand.nextInt(155),rand.nextInt(255),rand.nextInt(200)));
			g.drawRect(x, y, 1, 1);
		}
		
		//绘制干扰线 add by 夏同同  20160505
		g.setStroke(new BasicStroke(2.0f));
		for (int i = 0; i < 3; i++) {  
			int xs = rand.nextInt(width);
			int ys = rand.nextInt(height);
			int xe = rand.nextInt(width);
			int ye = rand.nextInt(height);
			g.setColor(new Color(rand.nextInt(155),rand.nextInt(255),rand.nextInt(200)));
			g.drawLine(xs, ys, xe, ye);  
		}
		
		//绘制验证码
		int codeY = height - 10;
		//设置字体颜色和样式
		g.setFont(new Font("Arial", Font.BOLD, fSize));
		for (int i = 0; i < codeLength; i++) {
			g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
			g.drawString(String.valueOf(securityCode.charAt(i)), i * fSize + 5, codeY);
		}
//		int w = image.getWidth();
//		int h = image.getHeight();
//		shear(g,w,h,Color.white);
		//关闭资源
		g.dispose();
		
		return image;
	}
	
	private static void shear(Graphics g, int w1, int h1, Color color) {
		Random generator = new Random();
		shearX(g, w1, h1, color, generator);
		shearY(g, w1, h1, color, generator);
	}

	public static void shearX(Graphics g, int w1, int h1, Color color, Random generator) {

		int period = generator.nextInt(2);

		boolean borderGap = true;
		int frames = 1;
		int phase = generator.nextInt(2);

		for (int i = 0; i < h1; i++) {
			double d = (period >> 1)
					* Math.sin((double) i / (double) period + (6.2831853071795862D * phase) / frames);
			g.copyArea(0, i, w1, 1, (int) d, 0);
			if (borderGap) {
				g.setColor(color);
				g.drawLine((int) d, i, 0, i);
				g.drawLine((int) d + w1, i, w1, i);
			}
		}

	}

	public static void shearY(Graphics g, int w1, int h1, Color color, Random generator) {

		int period = generator.nextInt(40) + 10; // 50;

		boolean borderGap = true;
		int frames = 20;
		int phase = 7;
		for (int i = 0; i < w1; i++) {
			double d = (period >> 1)
					* Math.sin((double) i / (double) period + (6.2831853071795862D * phase) / frames);
			g.copyArea(i, 0, 1, h1, 0, (int) d);
			if (borderGap) {
				g.setColor(color);
				g.drawLine(i, (int) d, i, 0);
				g.drawLine(i, (int) d + h1, i, h1);
			}

		}

	}
	
	/**
	 * 返回验证码图片的流格式
	 * @param securityCode 验证码
	 * @return ByteArrayInputStream  图片流
	 */
	public static ByteArrayInputStream getImageAsInputStream(String securityCode){
		
		BufferedImage image = createImage(securityCode);
		return convertImageToStream(image);
	}
	
	/**
	 * 将BufferedImage转换成ByteArrayInputStream
	 * @param image 图片
	 * @return ByteArrayInputStream  流
	 */
	private static ByteArrayInputStream convertImageToStream(BufferedImage image) {
		
		ByteArrayInputStream inputStream = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(bos);
		try {
			jpeg.encode(image);
			byte[] bts = bos.toByteArray();
			inputStream = new ByteArrayInputStream(bts);
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	
}
