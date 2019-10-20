package org.linbo.demo.rpc.client;

import java.util.HashMap;
import java.util.Map;

/**
 * 远程服务URI
 * @author linbo
 *
 */
public class RemoteServiceURI {
	
	private static final Map<Class<?>, String> remoteServiceUriMap = new HashMap<Class<?>, String>();;
	
	/**
	 * 获取远程服务地址
	 * @param service	接口服务实现
	 * @return
	 */
	public static String get(Object service) {
		Class<?> serviceType = service.getClass();
		for (Class<?> type : remoteServiceUriMap.keySet()) {
			if (type.isAssignableFrom(serviceType)) {
				return remoteServiceUriMap.get(type);
			}
		}
		return null;
	}
	
	/**
	 * 获取远程服务URI
	 * @param obj	服务实例
	 * @return
	 */
	public static void set(Class<?> serviceType, String uri) {
		remoteServiceUriMap.put(serviceType, uri);
	}

	
}
