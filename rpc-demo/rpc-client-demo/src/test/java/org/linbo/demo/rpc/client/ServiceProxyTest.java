package org.linbo.demo.rpc.client;

import org.junit.Test;

import static org.junit.Assert.*;

public class ServiceProxyTest {

    @Test
    public void testInvoke() throws Exception {
        ServiceProxyFactory factory = new ServiceProxyFactory();
        factory.setRemoteServiceURI("");
        factory.setServiceInterface(String.class);
        Object object = factory.getObject();
    }


}