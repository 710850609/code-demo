package org.linbo.demo.rpc.client;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.linbo.demo.rpc.client.http.HttpInvoker;
import org.linbo.demo.rpc.client.http.HttpResponseCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 数据访问代理服务</br>
 * 基于Http协议，远程调用数据访问服务
 * @author linbo
 *
 */
public class ServiceProxy implements MethodInterceptor{
	
	private static Logger logger = LoggerFactory.getLogger(ServiceProxy.class);

	/**
	 * 序列化接口参数成JSON字符串</br>
	 * 如果参数为null，则不做转化
	 * @param objects
	 * @return
	 * @throws JsonProcessingException
	 */
	private String[] parseParameters(Object... objects) throws JsonProcessingException {
		if (objects == null) {
			return null;
		}
		ObjectMapper objectMapper =new ObjectMapper();
		int size = objects.length;
		String[] params = new String[size];
		for (int i = 0; i < size; i++) {
			if (objects[i] != null) {
				params[i] = objectMapper.writeValueAsString(objects[i]);
			} else {
				params[i] = null;
			}
		}
		return params;
	}
	
	/**
	 * 调用远程服务，并把HTTP响应反序列化成DAL执行结果
	 * @param remoteURI	远程服务地址
	 * @param methodName	方法名
	 * @param parameters	参数列表
	 * @param clazz	返回类型
	 * @return
	 * @throws Exception 
	 */
	private <T> T invokeService(final String remoteURI, String methodName, String[] parameters,
			final Class<T> clazz) throws Exception {
		logger.debug("请求远程调用{}：{}", remoteURI, methodName);
		if (remoteURI == null || "".equals(remoteURI.trim())) {
			String msg = String.format("初始化WebClient失败：不存在有效的URI");
			throw new RuntimeException(msg);
		}
		final ObjectMapper objectMapper = new ObjectMapper();
		// 请求头
		Map<String, String> headers = new HashMap<String, String>(2);
		headers.put(HttpHeaders.CONTENT_TYPE, "application/json");
		// 请求体
		Map<String, Object> requstData = new HashMap<String, Object>(2);
		requstData.put("method", methodName);
		requstData.put("parameters", parameters);
//		String data = JSONObject.toJSONString(requstData);
		String data = objectMapper.writeValueAsString(requstData);
		// 发起请求
		return HttpInvoker.doPost(remoteURI, headers, data, new HttpResponseCallback<T>() {
			@Override
			public T doCall(int status, CloseableHttpResponse response) throws Exception {
				// 获取响应体数据
				HttpEntity httpEntity = response.getEntity();
				String responseStr = EntityUtils.toString(httpEntity, "UTF-8");
				// 根据状态码判断是否执行成功
				String msg = null;
				switch (status) {
				case 200:
					// 请求成功，处理响应体数据，如：
//					return JSONObject.parseObject(responseStr, clazz);
					return objectMapper.readValue(responseStr, clazz);
				case 400:
					// 客户端请求有误，记录响应数据
					logger.error("[{}]请求数据有误：{}", status, responseStr);
					throw new Exception(responseStr);
				case 404:
					msg = String.format("无效的远程服务地址：%s", remoteURI);
					logger.error(msg);
					throw new Exception(msg);
				case 500:
					// 服务端错误
					Map<String, Object> responseData = objectMapper.readValue(responseStr, new TypeReference<Map<String, Object>>(){});
					String code = (String) responseData.get("code");
					String describe = (String) responseData.get("describe");
					String errMsg = String.format("远程调用错误:{code：%s, describe：%s}", code, describe);
					logger.error(errMsg);
					throw new Exception(msg);
				default:
					// 未知结果类型，记录响应数据
					msg = String.format("未知的远程调用结果：{状态码：%d, 响应:%s}", status, responseStr);
					logger.error("未知请求返回结果：{}", msg);
					throw new Exception(msg);
				}
			}
		});
	}
		/**
	 * 生成错误返回对象
	 * @param returnType	返回类型
	 * @param rspType	
	 * @param rspCode
	 * @param rspDesc
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T newErrorReturnObject(Class<T> returnType, String rspType, String rspCode, String rspDesc) throws Exception {
		try {
			Object errorObj = returnType.newInstance();
			// 有些环境用这种方法调用 第2行代码会出现空指针
//			errorObj = returnType.getMethod("setRspType", String.class).invoke(errorObj, rspType);
//			errorObj = returnType.getMethod("setRspCode", String.class).invoke(errorObj, rspCode);
//			errorObj = returnType.getMethod("setRspDesc", String.class).invoke(errorObj, rspDesc);
			returnType.getMethod("setRspType", String.class).invoke(errorObj, rspType);
			returnType.getMethod("setRspCode", String.class).invoke(errorObj, rspCode);
			returnType.getMethod("setRspDesc", String.class).invoke(errorObj, rspDesc);
			return (T) errorObj;
		} catch (Exception e) {
			logger.error("反射构造服务返回对象错误:" + returnType.getName(), e);
			throw e;
		}
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] parameters, MethodProxy methodproxy) throws Throwable {
		// 执行 finalize 方法不做远程调用
		if ("finalize".equals(method.getName())) {
			return method.invoke(obj, parameters);
		}
		Class<?> returnType = method.getReturnType();
		try {
			// 将参数转成JSON字符串
			String[] params = parseParameters(parameters);
			// 获取当前方法名
			String methodName = method.getName();
			// 获取远程服务URI
			String remoteURI = RemoteServiceURI.get(obj);
			return invokeService(remoteURI, methodName, params, returnType);
		} catch (Exception e) {
			logger.error("远程调用服务错误", e);
			return newErrorReturnObject(returnType, "1", "ibus00", e.getMessage());
		}
	}

}
