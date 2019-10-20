package org.linbo.demo.rpc.server;

public interface RpcService {

	Object invoke(String requestStr) throws Exception;
	
}
