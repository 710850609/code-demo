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
@EnableConfigurationProperties(DemoProperties.class)
@ConditionalOnClass(Demo.class)
@ConditionalOnProperty(prefix = "linbo.demo", value = "open", havingValue = "true", matchIfMissing = true)
public class DemoAutoConfiguration {

    @Autowired
    private DemoProperties demoProperties;

    @Bean
    @ConditionalOnMissingBean(Demo.class)
    public Demo getDemo() {
        Demo demo = new Demo();
        demo.setName(demoProperties.getName());
        return demo;
    }

}
