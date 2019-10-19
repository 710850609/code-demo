/**
 * 
 */
package org.linbo.demo.crypto;

import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.GeneralSecurityException;

/**
 * 对称加解密工具类
 * 
 * @author yiyuan.chen
 * @version $Id: SymmtricCryptoUtil.java, v 0.1 2011-2-16 下午07:46:03 yiyuan.chen
 *          Exp $
 */
public class SymmtricCryptoUtil {
	/**
	 * 对称加解密(/CBC/PKCS5Padding模式) KMI默认的是/CBC/PKCS5Padding模式
	 * 
	 * @param text
	 *            待加/解密的数据
	 * @param keyData
	 *            密钥数据
	 * @param algorithm
	 *            对称加密算法名称。KMI默认使用3DES算法，即“DESede”. 目前KMI接受的参数有: AES, Blowfish,
	 *            DESede
	 * @param mode
	 *            加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
	 * @return 密文(加密)/明文（解密）。
	 * @throws GeneralSecurityException
	 *             当用户输入KMI不接受的参数时,会抛出异常 当密钥数据的长度不符合算法要求时,会抛出异常
	 */
	public static byte[] symmtricCrypto(byte[] text, byte[] keyData,
			String algorithm, int mode) throws GeneralSecurityException {
		String fullAlg = algorithm + "/CBC/PKCS5Padding";
		byte[] iv = initIv(fullAlg);
		return doCrypto(text, keyData, iv, fullAlg, "CBC", "PKCS5Padding", mode);
	}

	/**
	 * 对称加解密(/CBC/?模式) KMI默认的工作模式是CBC
	 * 
	 * @param text
	 *            待加/解密的数据
	 * @param keyData
	 *            密钥数据
	 * @param algorithm
	 *            对称加密算法名称。KMI默认使用3DES算法，即“DESede”. 目前KMI接受的参数有: AES, Blowfish,
	 *            DESede
	 * @param padding
	 *            填充模式,目前KMI接受的参数有PKCS5Padding和NoPadding.
	 * @param mode
	 *            加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
	 * @return 密文(加密)/明文（解密）。
	 * @throws GeneralSecurityException
	 *             当用户输入KMI不接受的参数时,会抛出异常 当密钥数据的长度不符合算法要求时,会抛出异常
	 *             在NoPadding填充模式下,当待加密的数据不是相应的算法的块大小的整数倍时,会抛出异常
	 */
	public static byte[] symmtricCrypto(byte[] text, byte[] keyData,
			String algorithm, String padding, int mode)
			throws GeneralSecurityException {
		String fullAlg = algorithm + "/CBC/" + padding;
		byte[] iv = initIv(fullAlg);
		return doCrypto(text, keyData, iv, fullAlg, "CBC", padding, mode);
	}

	/**
	 * 对称加解密(/?/?模式)
	 * 
	 * @param text
	 *            待加/解密的数据
	 * @param keyData
	 *            密钥数据
	 * @param algorithm
	 *            对称加密算法名称。KMI默认使用3DES算法，即“DESede”. 目前KMI接受的参数有: AES, Blowfish,
	 *            DESede
	 * @param workingMode
	 *            工作模式,目前KMI接受的参数有CBC和ECB.
	 * @param padding
	 *            填充模式,目前KMI接受的参数有PKCS5Padding和NoPadding.
	 * @param mode
	 *            加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
	 * @return 密文(加密)/明文（解密）。
	 * @throws GeneralSecurityException
	 *             当用户输入KMI不接受的参数时,会抛出异常 当密钥数据的长度不符合算法要求时,会抛出异常
	 *             在NoPadding填充模式下,当待加密的数据不是相应的算法的块大小的整数倍时,会抛出异常
	 */
	public static byte[] symmtricCrypto(byte[] text, byte[] keyData,
			String algorithm, String workingMode, String padding, int mode)
			throws GeneralSecurityException {
		String fullAlg = algorithm + "/" + workingMode + "/" + padding;
		byte[] iv = null;
		if (StringUtils.equals(workingMode, "CBC")) {
			iv = initIv(fullAlg);
		}
		return doCrypto(text, keyData, iv, fullAlg, workingMode, padding, mode);
	}

	/**
	 * 获取对称加解密输入流的方法。（/CBC/PKCS5Padding模式）
	 * 
	 * @param file
	 * @param keyData
	 *            密钥数据
	 * @param algorithm
	 *            对称加密算法名称。KMI默认使用3DES算法，即“DESede”. 目前KMI接受的参数有: AES, Blowfish,
	 *            DESede
	 * @param mode
	 *            加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
	 * @return 输入流
	 * @throws IOException
	 *             文件读取错误的时候抛出该异常。
	 * @throws GeneralSecurityException
	 *             加解密失败时抛出该异常。
	 */
	public static InputStream getInputStream(File file, byte[] keyData,
			String algorithm, int mode) throws IOException,
			GeneralSecurityException {
		return getInputStream(file, keyData, algorithm, "CBC", "PKCS5Padding",
				mode);
	}

	/**
	 * 获取对称加解密输入流的方法
	 * 
	 * @param file
	 * @param keyData
	 *            密钥数据
	 * @param algorithm
	 *            对称加密算法名称。KMI默认使用3DES算法，即“DESede”. 目前KMI接受的参数有: AES, Blowfish,
	 *            DESede
	 * @param workingMode
	 *            工作模式,目前KMI接受的参数有CBC和ECB.
	 * @param padding
	 *            填充模式,目前KMI接受的参数有PKCS5Padding和NoPadding.
	 * @param mode
	 *            加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
	 * @return 输入流
	 * @throws IOException
	 *             文件读取错误的时候抛出该异常。
	 * @throws GeneralSecurityException
	 *             加解密失败时抛出该异常。
	 */
	public static InputStream getInputStream(File file, byte[] keyData,
			String algorithm, String workingMode, String padding, int mode)
			throws IOException, GeneralSecurityException {
		String fullAlg = algorithm + "/CBC/PKCS5Padding";
		// 初始化输入流
		FileInputStream fileInputStream = new FileInputStream(file);
		// 初始化cipher
		byte[] iv = initIv(fullAlg);
		Cipher cipher = getCipher(keyData, iv, fullAlg, workingMode, mode);
		return new CipherInputStream(fileInputStream, cipher);
	}

	/**
	 * 获取对称加解密输出流的方法。（/CBC/PKCS5Padding模式）
	 * 
	 * @param file
	 * @param keyData
	 *            密钥数据
	 * @param algorithm
	 *            对称加密算法名称。KMI默认使用3DES算法，即“DESede”. 目前KMI接受的参数有: AES, Blowfish,
	 *            DESede
	 * @param mode
	 *            加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
	 * @return 输出流
	 * @throws IOException
	 *             文件读取错误的时候抛出该异常。
	 * @throws GeneralSecurityException
	 *             加解密失败时抛出该异常。
	 */
	public static OutputStream getOutputStream(File file, byte[] keyData,
			String algorithm, int mode) throws IOException,
			GeneralSecurityException {
		return getOutputStream(file, keyData, algorithm, "CBC", "PKCS5Padding",
				mode);
	}

	/**
	 * 获取对称加解密输出流的方法
	 * 
	 * @param file
	 * @param keyData
	 *            密钥数据
	 * @param algorithm
	 *            对称加密算法名称。KMI默认使用3DES算法，即“DESede”. 目前KMI接受的参数有: AES, Blowfish,
	 *            DESede
	 * @param workingMode
	 *            工作模式,目前KMI接受的参数有CBC和ECB.
	 * @param padding
	 *            填充模式,目前KMI接受的参数有PKCS5Padding和NoPadding.
	 * @param mode
	 *            加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
	 * @return 输出流
	 * @throws IOException
	 *             文件读取错误的时候抛出该异常。
	 * @throws GeneralSecurityException
	 *             加解密失败时抛出该异常。
	 */
	public static OutputStream getOutputStream(File file, byte[] keyData,
			String algorithm, String workingMode, String padding, int mode)
			throws IOException, GeneralSecurityException {
		String fullAlg = algorithm + "/CBC/PKCS5Padding";
		// 初始化输出流
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		// 初始化cipher
		byte[] iv = initIv(fullAlg);
		Cipher cipher = getCipher(keyData, iv, fullAlg, workingMode, mode);
		return new CipherOutputStream(fileOutputStream, cipher);
	}

	/**
	 * 实现加解密的方法
	 * 
	 * @param text
	 *            待加/解密的数据
	 * @param keyData
	 *            密钥数据
	 * @param iv
	 *            初始向量
	 * @param fullAlg
	 *            对称加密算法全名。eg.DESede/CBC/PKCS5Padding
	 * @param padding
	 *            填充模式,目前KMI接受的参数有PKCS5Padding和NoPadding.
	 * @param mode
	 *            加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
	 * @return 密文(加密)/明文（解密）。
	 * @throws GeneralSecurityException
	 *             当用户输入KMI不接受的参数时,会抛出异常 当密钥数据的长度不符合算法要求时,会抛出异常
	 *             在NoPadding填充模式下,当待加密的数据不是相应的算法的块大小的整数倍时,会抛出异常
	 */
	public static byte[] doCrypto(byte[] text, byte[] keyData, byte[] iv,
			String fullAlg, String workingMode, String padding, int mode)
			throws GeneralSecurityException {
		if (!StringUtils.equals(workingMode, "CBC")
				&& !StringUtils.equals(workingMode, "ECB")) {
			throw new GeneralSecurityException("错误的工作模式,目前KMI只支持CBC和ECB两种工作模式");
		}

		if (!StringUtils.equals(padding, "PKCS5Padding")
				&& !StringUtils.equals(padding, "NoPadding")) {
			throw new GeneralSecurityException(
					"错误的填充模式,目前KMI只支持PKCS5Padding和NoPadding两种工作模式");
		}

		if (mode != Cipher.ENCRYPT_MODE && mode != Cipher.DECRYPT_MODE) {
			throw new GeneralSecurityException(
					"错误的加解密标识,目前KMI只支持Cipher.ENCRYPT_MODE和Cipher.DECRYPT_MODE");
		}

		Cipher cipher = getCipher(keyData, iv, fullAlg, workingMode, mode);
		return cipher.doFinal(text);
	}

	/**
	 * 根据参数初始化cipher的方法
	 * 
	 * @param keyData
	 *            密钥数据
	 * @param fullAlg
	 *            用来初始化Cipher对象的算法全称(已经加上工作模式和填充模式的)
	 * @param workingMode
	 *            工作模式,目前KMI接受的参数有CBC和ECB.
	 * @param padding
	 *            填充模式,目前KMI接受的参数有PKCS5Padding和NoPadding.
	 * @param mode
	 *            加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
	 * @return cipher
	 * @throws GeneralSecurityException
	 */
	private static Cipher getCipher(byte[] keyData, byte[] iv, String fullAlg,
			String workingMode, int mode) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(fullAlg);
		SecretKey secretKey = new SecretKeySpec(keyData, StringUtils
				.substringBefore(fullAlg, "/"));

		if (StringUtils.equals(workingMode, "CBC")) {
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			cipher.init(mode, secretKey, ivSpec);
		} else {
			cipher.init(mode, secretKey);
		}
		return cipher;
	}

	/**
	 * 初始向量的方法
	 * 
	 * @param fullAlg
	 * @return
	 * @throws GeneralSecurityException
	 */
	private static byte[] initIv(String fullAlg)
			throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(fullAlg);
		int blockSize = cipher.getBlockSize();
		byte[] iv = new byte[blockSize];
		for (int i = 0; i < blockSize; ++i) {
			iv[i] = 0;
		}
		return iv;
	}
}
