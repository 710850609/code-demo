package org.linbo.demo.springboot.starter;

/**
 * @author LinBo
 * @date 2019-10-11 17:46
 */
public class Demo {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void handle() {
        System.out.println("Demo name = " + name);
    }
}
