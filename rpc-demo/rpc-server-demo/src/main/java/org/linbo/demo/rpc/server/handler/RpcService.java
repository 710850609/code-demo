package org.linbo.demo.rpc.server.handler;

public interface RpcService {

	Object invoke(String requestStr) throws Exception;
	
}
