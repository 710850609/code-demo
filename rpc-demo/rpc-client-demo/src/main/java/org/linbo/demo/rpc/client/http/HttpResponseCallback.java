package org.linbo.demo.rpc.client.http;

import org.apache.http.client.methods.CloseableHttpResponse;

/**
 * http响应回调，用于处理响应结果
 * @author linbo
 *
 * @param <T>	返回类型
 */
public interface HttpResponseCallback<T> {

	public T doCall(int status, CloseableHttpResponse response) throws Exception; 
}
