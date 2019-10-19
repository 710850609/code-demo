package org.linbo.demo.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class VerificationCode {
	private static final int SIZE = 4;
	private static final int WIDTH = 70;
	private static final int HEIGHT = 25;
	private static final int FONT_SIZE = 28;
	private static char[] chars = {'0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' };

	/**
	 * 获取验证码的字符串和图片
	 * @return
	 */
	public static Map<String, BufferedImage> createImage() {
		Map<String,BufferedImage> map = new HashMap<String,BufferedImage>();
		Random r = new Random();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		StringBuffer code = new StringBuffer();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//由随机字符串生成图片
		g.setFont(new Font(null,Font.BOLD+Font.ITALIC,FONT_SIZE));
		for(int i=1; i<=SIZE; i++){
			g.setColor(getRandomColor());
			int index = r.nextInt(chars.length);
			g.drawString(chars[index]+"", (i-1)*WIDTH/SIZE, HEIGHT-2);
			code.append(chars[index]);
		}
		//画干扰线
		for(int i=0; i<8; i++){
			g.setColor(getRandomColor());
			g.drawLine(r.nextInt(WIDTH),r.nextInt(HEIGHT),r.nextInt(WIDTH),r.nextInt(HEIGHT));
		}
		//将验证码和图片放入map中
		map.put(code.toString(), image);
		return map;
	}
	/**
	 * 产生随机颜色用于填充字体
	 * @return
	 */
	private static Color getRandomColor() {
		Random r = new Random();
		return new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
	}
	public static InputStream getInputStream(BufferedImage image) throws ImageFormatException, IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
		encoder.encode(image);
		InputStream ips = new ByteArrayInputStream(bos.toByteArray());
		return ips;
	}

}
