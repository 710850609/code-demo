package org.linbo.demo.springboot.starter;

/**
 * 这个类是为了模拟第三方组件,一般第三方jar,不会和starter项目冗余一起
 * @author LinBo
 * @date 2019-10-11 17:46
 */
public class DemoClient {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void doRequest() {
        System.out.println();
        System.out.println("当前 DemoClient name: ");
        System.out.println(name);
        System.out.println();
    }
}
