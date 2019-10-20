//package org.linbo.demo.rpc.server.config;
//
//import org.apache.cxf.Bus;
//import org.apache.cxf.endpoint.Server;
//import org.apache.cxf.feature.LoggingFeature;
//import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
//import org.linbo.demo.rpc.server.http.RpcServiceHttpImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Arrays;
//
///**
// * @author LinBo
// * @date 2019-10-19 23:46
// */
////@Configuration
////public class CxfConfig {
////
////    @Autowired
////    private Bus bus;
////
////    @Bean
////    public Server rsServer() {
////        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
////        endpoint.setBus(bus);
////        endpoint.setServiceBeans(Arrays.<Object>asList(new RpcServiceHttpImpl()));
////        endpoint.setAddress("/");
//////        endpoint.setFeatures(Arrays.asList(new LoggingFeature()));
////        return endpoint.create();
////    }
//
////    @Bean
//
////    public OpenApiFeature createOpenApiFeature() {
////        final OpenApiFeature openApiFeature = new OpenApiFeature();
////        openApiFeature.setPrettyPrint(true);
////        openApiFeature.setTitle("Spring Boot CXF REST Application");
////        openApiFeature.setContactName("The Apache CXF team");
////        openApiFeature.setDescription("This sample project demonstrates how to use CXF JAX-RS services"
////                + " with Spring Boot. This demo has two JAX-RS class resources being"
////                + " deployed in a single JAX-RS endpoint.");
////        openApiFeature.setVersion("1.0.0");
////        openApiFeature.setSwaggerUiConfig(
////                new SwaggerUiConfig()
////                        .url("/services/helloservice/openapi.json"));
////        return openApiFeature;
////    }
////}
