package org.linbo.demo.pattern.adapter;

/**
 * 新打印功能
 * @author 71085
 *
 */
public interface INewPrint {

	/**
	 * 打印功能
	 * @param auth	打印凭证
	 * @param str	打印内容
	 * @return 
	 */
	void print(String auth, String str);
}
