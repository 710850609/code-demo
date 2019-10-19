package org.linbo.demo.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 谷歌提供的工具
 * 实现参考：
 * https://www.jianshu.com/p/05e9ee773898
 * https://blog.csdn.net/qq_21916331/article/details/52117420
 * 
 * @author 71085
 *
 */
public class QrCode {

	public static final int DEFAULT_WIDTH = 300;// 默认二维码宽度
	public static final int DEFAULT_HEIGHT = 300;// 默认二维码高度
	private static final String format = "png";// 默认二维码文件格式
	private static final Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();// 二维码参数

	static {
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 字符编码
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 容错等级 L、M、Q、H 其中 L 为最低, H 为最高
		hints.put(EncodeHintType.MARGIN, 2);// 二维码与图片边距
	}

	/**
	 * 返回一个 BufferedImage 对象
	 * 
	 * @param content 二维码内容
	 * @param width   宽
	 * @param height  高
	 */
	public static BufferedImage toBufferedImage(String content, int width, int height)
			throws WriterException, IOException {
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

	/**
	 * 将二维码图片输出到一个流中
	 * 
	 * @param content 二维码内容
	 * @param stream  输出流
	 * @param width   宽
	 * @param height  高
	 */
	public static void writeToStream(String content, OutputStream stream, int width, int height)
			throws WriterException, IOException {
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
	}

	/**
	 * 生成二维码图片文件
	 * 
	 * @param content 二维码内容
	 * @param path    文件保存路径
	 * @param width   宽
	 * @param height  高
	 */
	public static void createQRCode(String content, String path, int width, int height)
			throws WriterException, IOException {
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		// toPath() 方法由 jdk1.7 及以上提供
		MatrixToImageWriter.writeToPath(bitMatrix, format, new File(path).toPath());
	}
}
