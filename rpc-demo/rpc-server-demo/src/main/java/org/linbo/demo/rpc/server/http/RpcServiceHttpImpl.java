package org.linbo.demo.rpc.server.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.linbo.demo.rpc.server.RpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service("httpAgentService")
public class RpcServiceHttpImpl implements RpcService {
	
	private static Logger logger = LoggerFactory.getLogger(RpcServiceHttpImpl.class);
	
	private Object targetService;
	
	public void setTargetService(Object targetService) {
		this.targetService = targetService;
	}

	@Override
	public Object invoke(String requestStr) throws Exception {
		Objects.requireNonNull(targetService, "未注入代理服务对象");
		return invokeTargetMethod(targetService, requestStr);
	}

	/**
	 * 执行目标方法
	 * @param targetObject	目标对象
	 * @param requestStr	请求字符串</br>
	 * 格式:</br>
	 * <pre>
	 * {
	 * 	"method":"", // 目标方法名
	 * 	"parameters":[] // 参数列表，元素为参数对应JSON格式字符串。如果参数为null，则对应null
	 * }
	 * </pre>
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private Object invokeTargetMethod(Object targetObject, String requestStr) throws Exception {
		Map<String, Object> requestParams = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			requestParams = mapper.readValue(requestStr, new TypeReference<Map<String, Object>>() {});
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("JSON反序列化失败", e);
			throw new IllegalArgumentException("代理服务端错误");
		}
		 
		String methodName = (String) requestParams.get("method");
		if (StringUtils.isEmpty(methodName)) {
			throw new IllegalArgumentException("缺失协议请求参数method");
		}
		logger.debug("请求远程调用 {}.{}", targetObject.getClass().getName(), methodName);
		List<String> parameters = null;
		try {
			parameters = (List<String>) requestParams.get("parameters");
			if (parameters == null) {
                throw new IllegalArgumentException("缺失协议请求参数parameters");
			}
		} catch (Exception e) {
			e.printStackTrace();
            throw new IllegalArgumentException("协议参数parameters错误");
		}
		
		Method[] methods = targetObject.getClass().getDeclaredMethods();
		for (Method method : methods) {
			Class<?>[] parameterTypes = method.getParameterTypes();
			int parameterTypeSize = parameterTypes.length;
			int parameterSize = parameters.size();
			if (method.getName().equals(methodName) && parameterSize == parameterTypeSize) {
				// 兼容方法重载
				Object[] targetParameters = parseParameters(method, parameters);
				try {
					// 调用目标方法
					Object result = method.invoke(targetObject, targetParameters);
					logger.debug("远程调用 完成：{}.{}", targetObject.getClass().getName(), methodName);
					return result;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					logger.error("调用 {}.{} 出现IllegalAccessException异常：{}", 
							targetObject.getClass().getName(), method, e.getMessage());
                    throw new IllegalArgumentException("不支持的HTTP方法");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					logger.error("调用 {}.{} 出现IllegalArgumentException异常：{}", 
							targetObject.getClass().getName(), method, e.getMessage());
                    throw new IllegalArgumentException("参数错误");
				} catch (InvocationTargetException e) {
					e.getTargetException().printStackTrace();
					logger.error("调用 {}.{} 出现InvocationTargetException异常：{}",
							targetObject.getClass().getName(), method, e.getTargetException().getMessage());
					String msg = String.format("调用API失败:%s", e.getMessage());
					throw new Exception(msg);
				}
			}
		}
		// 不存在指定方法
		String errMsg = String.format("未匹配 %s 方法，请确认方法名和参数列表有效", methodName);
        throw new IllegalArgumentException(errMsg);
	}
	
	/**
	 * 解析JSON字符串集成目标方法的参数，兼容方法重载
	 * @param method	目标方法
	 * @param parameters	JSON字符串参数
	 * @return
	 */
	private Object[] parseParameters(Method method, List<String> parameters) {
		Class<?>[] parameterTypes = method.getParameterTypes();
		Object[] targetParameters = new Object[parameterTypes.length];
		boolean hasFound = false;
		// 兼容方法重载
		ObjectMapper objectMapper = new ObjectMapper();
		for (int i = 0; i < parameters.size(); i++) {
			try {
				String param = parameters.get(i);
				Class<?> type = parameterTypes[i];
				Object value = param;
				if (param != null) {
					if (type.getGenericSuperclass() instanceof Class<?>) {
						// 非泛型类型
						value = objectMapper.readValue(param, type);
					} else {
						Type[] types = method.getGenericParameterTypes();
						final Type pType = types[i];
						if (!pType.getClass().isPrimitive()) {
							// 泛型类型
							value = objectMapper.readValue(param, new TypeReference<String>() {
								@Override
								public Type getType() {
									return pType;
								}
							});
						} else {
							// Java基本类型，使用JSON反序列化后的值（包装类）
							
						}
					}
				}
				targetParameters[i] = value;
				hasFound = true;
			} catch (IOException e) {
				logger.warn("反序列化参数列表失败，继续查询其他重载方法", e);
				hasFound = false;
				continue;
			}
		}
		if (!hasFound) {
			String msg = String.format("不存在方法：%s(%s)", method.getName(), Arrays.toString(parameterTypes));
			throw new IllegalArgumentException(msg);
		}
		return targetParameters;
	}
	

}
