package org.linbo.demo.springboot.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LinBo
 * @date 2019-10-11 17:21
 */
@Configuration
@EnableConfigurationProperties(DemoClientProperties.class)
@ConditionalOnClass(DemoClient.class)
@ConditionalOnProperty(prefix = "linbo.demo-client", value = "open", havingValue = "true", matchIfMissing = true)
public class DemoClientAutoConfiguration {

    @Autowired
    private DemoClientProperties demoClientProperties;

    @Bean
    @ConditionalOnMissingBean(DemoClient.class)
    public DemoClient getDemoClient() {
        DemoClient demo = new DemoClient();
        demo.setName(demoClientProperties.getName());
        return demo;
    }

}
