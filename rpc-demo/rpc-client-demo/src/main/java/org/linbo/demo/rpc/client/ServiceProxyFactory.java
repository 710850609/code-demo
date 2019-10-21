package org.linbo.demo.rpc.client;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import net.sf.cglib.proxy.Enhancer;

/**
 * 数据访问层服务代理工厂</br>
 * 作用：生成数据访问层接口对应的动态代理实现类
 * <pre>在spring配置文件中使用如下配置：
 * 
 *	&lt;bean id="dataServiceProxy" class="com.smart.ibus.dal.agent.DalServiceProxyFactory"&gt;
 *	&nbsp;&nbsp;&lt;property name="serviceInterface" value="com.smartibus.dal.intf.DataService" /&gt;
 *	&nbsp;&nbsp;&lt;property name="remoteServiceURI" value="http://192.168.7.200:9000/rest/services/dal/v1.0/dal/data" /&gt;
 *	&lt;/bean&gt;
 * </pre>
 * @author linbo
 *
 */
public class ServiceProxyFactory implements InitializingBean, FactoryBean<Object>{

	/**
	 * 代理类
	 */
	private Object proxyService;
	
	/**
	 * 代理接口
	 */
	private Class<?> serviceInterface;

	/**
	 * 接口对应远程服务
	 */
	private String remoteServiceURI;
	
	public void setServiceInterface(Class<?> serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	public void setRemoteServiceURI(String remoteServiceURI) {
		this.remoteServiceURI = remoteServiceURI;
	}

	/**
	 * 获取代理对象 
	 */
	@Override
	public Object getObject() throws Exception {
		return proxyService;
	}

	/**
	 * 获取代理接口类型
	 */
	@Override
	public Class<?> getObjectType() {
		return serviceInterface;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (serviceInterface == null) {
			throw new RuntimeException("代理接口为空");
		}
		if (remoteServiceURI == null || "".equals(remoteServiceURI)) {
			throw new RuntimeException("远程服务地址为空");
		}
		// 存放接口服务和对应远程服务地址
		RemoteServiceURI.set(serviceInterface, remoteServiceURI);
		// 使用Cglib生成动态代理
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(serviceInterface);
		enhancer.setCallback(new ServiceProxy());
		proxyService = enhancer.create();
	}
}
