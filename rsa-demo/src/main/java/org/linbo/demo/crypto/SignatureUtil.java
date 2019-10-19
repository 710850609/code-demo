/**
 * 
 */
package org.linbo.demo.crypto;

import org.apache.commons.lang.StringUtils;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 签名工具类
 * 
 * @author yiyuan.chen
 * @version $Id: SignatureUtil.java, v 0.1 2011-2-25 上午10:47:54 yiyuan.chen Exp
 *          $
 */
public class SignatureUtil {

	/**
	 * 使用私钥进行签名的方法
	 * 
	 * @param text
	 *            待签名的数据
	 * @param privateKeyData
	 *            私钥数据
	 * @param algorithm
	 *            签名算法,目前KMI支持NONEwithRSA, MD2withRSA, MD5withRSA, SHA1withRSA,
	 *            SHA256withRSA, SHA384withRSA, SHA512withRSA , SHA1withDSA
	 * @return 签名后的数据
	 * @throws GeneralSecurityException
	 */
	public static byte[] sign(byte[] text, byte[] privateKeyData,
			String algorithm) throws GeneralSecurityException {
		PrivateKey privateKey = getPrivateKey(privateKeyData, algorithm);
		Signature signatureChecker = Signature.getInstance(algorithm);
		signatureChecker.initSign(privateKey);
		signatureChecker.update(text);
		return signatureChecker.sign();
	}

	/**
	 * 使用公钥进行验签的方法
	 * 
	 * @param text
	 *            原始数据数据
	 * @param signedText
	 *            签名过的数据
	 * @param publicKeyData
	 *            公钥数据
	 * @param algorithm
	 *            签名算法,目前KMI支持NONEwithRSA, MD2withRSA, MD5withRSA, SHA1withRSA,
	 *            SHA256withRSA, SHA384withRSA, SHA512withRSA , SHA1withDSA
	 * @return 如果验签成功,返回true,验签失败,返回false
	 * @throws GeneralSecurityException
	 */
	public static boolean verify(byte[] text, byte[] signedText,
			byte[] publicKeyData, String algorithm)
			throws GeneralSecurityException {
		PublicKey publicKey = getPublicKey(publicKeyData, algorithm);
		Signature signatureChecker = Signature.getInstance(algorithm);
		signatureChecker.initVerify(publicKey);
		signatureChecker.update(text);
		return signatureChecker.verify(signedText);
	}

	/**
	 * 得到公钥
	 * 
	 * @param keyData
	 *            密钥数据
	 * @throws GeneralSecurityException
	 */
	public static PublicKey getPublicKey(byte[] keyData, String algorithm)
			throws GeneralSecurityException {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyData);
		KeyFactory keyFactory = KeyFactory.getInstance(StringUtils
				.substringAfter(algorithm, "with"));
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 得到私钥
	 * 
	 * @param keyData
	 *            密钥数据
	 * @throws GeneralSecurityException
	 */
	public static PrivateKey getPrivateKey(byte[] keyData, String algorithm)
			throws GeneralSecurityException {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyData);
		KeyFactory keyFactory = KeyFactory.getInstance(StringUtils
				.substringAfter(algorithm, "with"));
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
}