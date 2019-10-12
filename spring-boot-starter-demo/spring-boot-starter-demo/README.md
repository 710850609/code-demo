# 自定义spring-boot-starter

### 一、背景
#### 1、问题产生
java项目经常使用到第三方工具，如Mybatis、Hibernate、MonogoDB、Redis等。

这些工具官方提供了客户端，但客户端初始化的时候，需要各种参数进行配置。从spring以往的bean管理方式看，有xml和@Bean两种方式。

如果使用到的工具数量一上来，项目源码中就有过多的配置相关东西，这块开发耗时就上来了，而且项目有较多的配置代码或xml。

#### 2、问题分析
这些客户端的初始化过程其实都大同小异，主要差别在参数设置。

spring定义了一套 读取配置文件 -> 构建java对象 的 [规范](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/boot-features-developing-auto-configuration.html#boot-features-custom-starter)。

按照spring的规范进行java bean初始化，业务项目中只需要引入自动化构建依赖和对应参数配置(有些可能不用，有默认参数)，这样项目就比较精简了。


### 二、创建工程
#### 1、创建一个普通spring boot项目

官方项目artifactId命令规范为: spring-boot-starter-[xxx]

非官方项目artifactId命令规范为: [xxx]-spring-boot-starter


#### 2、导入jar依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
<!--用于编译时生成 META-INF/spring-configuration-metadata.json 。此文件给IDE提示跳转等时使用。-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-autoconfigure</artifactId>
</dependency>
```
### 三、创建配置读取类
#### 1、命名规范
xxxProperties

#### 2、设置配置前缀
使用 @ConditionalOnProperty ，这里的前缀类似命名空间的概念，如： spring.datasource

#### 2、配置映射
xxxProperties中每一个属性对应properties或yml文件中的变量，key值为 [xxxProperties配置前缀 + "." + 属性名]

### 四、创建AotoConfig类
#### 1、命名规范
XxxAutoConfiguration

#### 2、使用注解进行条件初始化复杂对象

| 注解 | 举例 | 说明 |
| ---- | ---- | ---- |
| ```@Configuration``` | ```@Configuration``` | 声明该类是配置类 |
| ```@EnableConfigurationProperties``` | ```@EnableConfigurationProperties(DemoProperties.class)``` | spring加载类DemoProperties |
| ```@ConditionalOnProperty``` | ```@ConditionalOnProperty(prefix = "linbo.demo", value = "open", havingValue = "true", matchIfMissing = true)``` | 检查配置文件中是否有linbo.demo开头，如果linbo.demo.open=true，则自动配置，如果属性缺失,仍然自动配置 | 
| ```@ConditionalOnClass``` | ```@ConditionalOnClass(Demo.class)``` | 当前类路径下有Demo类型时, 才进行自动配置 | 
| ```@ConditionalOnBean``` | ```@ConditionalOnBean(Demo.class)``` | 当前容器加载到Demo类时, 才进行自动配置 | 
| ```@ConditionalOnExpression``` | ```@ConditionalOnExpression``` | 基于SpEL表达式作为判断条件 | 
| ```@ConditionalOnJava``` | ```@ConditionalOnJava``` | 基于JVM版本作为判断条件 | 
| ```@ConditionalOnJndi``` | ```@ConditionalOnJndi``` | 在JNDI存在的条件下查找指定的位置 | 
| ```@ConditionalOnMissingBean``` | ```@ConditionalOnMissingBean``` | 当容器中没有指定Bean的情况下 | 
| ```@ConditionalOnMissingClass``` | ```@ConditionalOnMissingClass``` | 当类路径下没有指定的类的条件下 | 
| ```@ConditionalOnNotWebApplication``` | ```@ConditionalOnNotWebApplication``` | 当前项目不是Web项目的条件下 | 
| ```@ConditionalOnSingleCandidate``` | ```@ConditionalOnSingleCandidate``` | 当指定的Bean在容器中只有一个的条件下 | 


### 五、注册自动配置类
在 src/main/resources/META-INF/spring.factories 文件夹中编写自动配置类的全路径
```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  org.linbo.demo.springboot.starter.DemoAutoConfiguration
```




