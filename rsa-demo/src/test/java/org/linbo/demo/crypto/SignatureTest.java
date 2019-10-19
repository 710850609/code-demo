package org.linbo.demo.crypto;

import junit.framework.TestCase;

/**
 * @author jun.lijun
 * 
 */
public class SignatureTest extends TestCase {

	/** 私钥 */
	private String PRI_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKm7ZeqAGQAWd2sqlw+UUnxG1Ox46VzS1yPyJf83bxasKAQAaqxzUgAfCVFECcWtfi83fMSWqSRa9mhS/KiZzlpZd1NdWbHa9XsLQdg6SKpdNy2Lkg5oqj6itbwIIp/n2yP4okt1WsZc0sL63x4hME705Y6SZS8iAlGmh0g73i4PAgMBAAECgYEAiN6FIMSLZHa5dcGybu28VlsH+GjIRgH/Ww05dsTM4AuTeSac3tNfnJlqeg3Tk4SD7K9h/WVAoAB6E0ZYV90veHi4Ud2tgd8eu7ZG0aBnDbIpq5j/H1u7V2Uq+jdKlsec9b1Yrkv+B30wbO6PWjoKsOd9NHI0NxQfmXFEmYL4XoECQQDu9+McDFHzdzcuD5IJuFIy2FF/CsloaZrQG4RQC3B1utFDBs/Ttxxyr9hr+2ZutCeLSpktspn9sSIlO9UMdIadAkEAtdRBaa9/nspSpK7Jct3ICdwyTKaeY7esRK3AgUVjSUNKcaSSeQtZVQ4sXXWKO8zwX4ntVe3783TZjCeq881RmwJBAMFtBnhyhHb0PUbcNZIGjwo2zChv27zXFgdy7nM3/yxcLfv57IjrA2zVZvFoiW38BrIZjMlPcpPFJ+VJe9ZcD4UCQCor8H/ylEbf5gsL/u0FQ0AGMiRVAKdgEjRbmCpjTYPQd5Oj99TbLPztLJ0Ahe8YuSdvdfxYFfeBQnOfP6cMF8ECQQCKF9QuzkoXPhrSFJe2x43NNqN1+8I6aHemZCLxIjEYWlG5UWKC/ocK/nsyimb44IMJh3Tkaoqglf+Vu6AAwb/e";

	/** 公钥 */
	private String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCpu2XqgBkAFndrKpcPlFJ8RtTseOlc0tcj8iX/N28WrCgEAGqsc1IAHwlRRAnFrX4vN3zElqkkWvZoUvyomc5aWXdTXVmx2vV7C0HYOkiqXTcti5IOaKo+orW8CCKf59sj+KJLdVrGXNLC+t8eITBO9OWOkmUvIgJRpodIO94uDwIDAQAB";

	/** 
	 * 公钥 *
	 */
	private String DES_KEY="aCZo37ykDqdRYelewcHLlBZJlxMV5l1P";
	
	
	/** 明文 */
//	private final static String TEST_TEXT = "<?xml version=\"1.0\" encoding=\"utf-8\"?><smart><applys><apply><school_no>123</school_no><card_no>2010508408</card_no><student_id>2010508408</student_id><student_name>葛有琴</student_name><apply_id>201501301315280000760366</apply_id><apply_amount>100</apply_amount><recharge_type>10</recharge_type></apply></applys></smart>";
	private final static String TEST_TEXT = "00AAA01011111";

	/** 加签方式 */
	private final static String ALGORITHM = "SHA1withRSA";
	
	/**
	 * 加密方式
	 */
	private final static String DESede = "DESede";
	
	/**
	 * 测试加密及验签
	 */
	public void testRSA() {
		SignUtils signUtils = new SignUtilsImpl();
		String signText = signUtils.sign(TEST_TEXT, PRI_KEY, ALGORITHM);
		System.out.println("singText:" + signText);
		assertTrue(signUtils.verify(TEST_TEXT, signText, PUB_KEY, ALGORITHM));
		
		
		String des=signUtils.encrypt(TEST_TEXT, DES_KEY, DESede);
		String ming = signUtils.decrypt(des, DES_KEY, DESede);
		
		System.out.println("明文："+TEST_TEXT);
		System.out.println("密文："+des);
		System.out.println("解密："+ming);
		
		String des1=signUtils.encrypt(TEST_TEXT, DES_KEY, DESede);
		System.out.println("密文比较：" + des.equals(des1));
		
		String test = "xzxkAeMfPkE2Q+90pWZIQm65Dvq447z8I840OrrTYpsoWDo5R8YUug3zgLQBLdNB3tJC/5DKw/w/nvQB2yNHNAIJEkyKgiti+vTOWaGCa/9ttW690b602f8XzMDsNsW9lOHiDTcsgGWC9ZfjvJi1eTnt06adoimrz7GN7GszDoJAluQ4trTlolOtqlBEOQQOfpnTGlQSFVdOuZ9K9gx3Z48Oh4EcDyD2pU1U52395W6j+16PuxifSDfffwCQniX9g2teeJqCKvtoZgx8XCc1bfFxchgLJVtii6qNx0XcGThMrQ43mvRwvDGBFgF+YURSfQ3c0RzvBf2y3DI2GNxRxAV5P+4zVzscG9Ia+jHxXXwJQDrOC0dRPHRwotjKHUZW+96Ep0M3Z4YZpL74AyO52UuE7cbLSU4pmZvTF+jU4Vm2fFXGBSnbkw==";
		test = signUtils.decrypt(test,  DES_KEY, DESede);
		System.out.println(test);
	}
}
