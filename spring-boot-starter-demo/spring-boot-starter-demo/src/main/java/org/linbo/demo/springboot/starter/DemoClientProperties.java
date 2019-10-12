package org.linbo.demo.springboot.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LinBo
 * @date 2019-10-11 17:33
 */
@ConfigurationProperties(prefix = "linbo.demo-client")
public class DemoClientProperties {

    private static final String DEFAULT_NAME =
            "|   \\| __|  \\/  |/ _ \\ \n" +
                    "| |) | _|| |\\/| | (_) |\n" +
                    "|___/|___|_|  |_|\\___/ \n";

    private String name = DEFAULT_NAME;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
