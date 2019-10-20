package org.linbo.demo.rpc.server.handler;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 远程调用服务接口
 * @author linbo
 *
 */
@RestController
@RequestMapping("/api")
public class RpcController {
	
	@Resource
	private RpcService rpcService;

	@RequestMapping("")
	public Object doRequest(@RequestBody String requestStr) throws Exception {
		return rpcService.invoke(requestStr);
	}
	
}
