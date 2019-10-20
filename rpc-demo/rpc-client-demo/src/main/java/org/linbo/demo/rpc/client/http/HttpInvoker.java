package org.linbo.demo.rpc.client.http;


import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


/**
 * HTTP 调用者
 * @author linbo
 *
 */
public class HttpInvoker {

	/**
	 * 发起POST请求，请求类型为JSON，
	 * @param uri	请求URI
	 * @param data	请求体数据
	 * @param callback	响应回调
	 * @return
	 * @throws Exception
	 */
	public static <T> T doPost(String uri, Map<String, String> headers, String data, HttpResponseCallback<T> callback) throws Exception {
		// 设置请求头
		HttpPost post = new HttpPost(uri);
		if (headers != null && headers.size() > 0) {
			for (String key : headers.keySet()) {
				post.addHeader(key, headers.get(key));
			}
		}
		
		// 设置请求体
		if (data != null) {
			post.setEntity(new StringEntity(data, "UTF-8"));
		}
		
		// 调用远程服务
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(post);
		// 获取响应状态码
		int status = response.getStatusLine().getStatusCode();
		return callback.doCall(status, response);
	
	}
}
