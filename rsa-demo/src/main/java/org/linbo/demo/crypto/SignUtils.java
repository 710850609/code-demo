/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package org.linbo.demo.crypto;

/**
 * 
 * @author geng.lin
 * @version $Id: SignUtils.java, v 0.1 2012-3-6 下午11:00:32 geng.lin Exp $
 */
public interface SignUtils {

    /**
     * 加签
     * 
     * @param text 明文
     * @param key 密钥
     * @param algorithm 签名算法,目前KMI支持NONEwithRSA, MD2withRSA, MD5withRSA, SHA1withRSA, SHA256withRSA, SHA384withRSA, SHA512withRSA , SHA1withDSA
     * @return 签名
     * @throws Exception
     */
    public String sign(final String text, final String key, final String algorithm);

    /**
     * 验签
     * 
     * @param text 明文
     * @param signText 签名
     * @param key 密钥
     * @param algorithm 验签算法,目前KMI支持NONEwithRSA, MD2withRSA, MD5withRSA, SHA1withRSA, SHA256withRSA, SHA384withRSA, SHA512withRSA , SHA1withDSA
     * @return 验签通过返回true，不通过返回false
     * @throws Exception
     */
    public boolean verify(final String text, final String signText, final String key,
                          final String algorithm);

    /**
     * 加密
     * 
     * @param text 明文
     * @param key 密钥
     * @param algorithm 算法
     * @return 密文
     */
    public String encrypt(final String text, final String key, final String algorithm);

    /**
     * 解密
     * 
     * @param text 密文
     * @param key 密钥
     * @param algorithm 算法 
     * @return 明文
     */
    public String decrypt(final String text, final String key, final String algorithm);
}
