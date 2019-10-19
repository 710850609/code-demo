# 常用设计模式
## 一、适配器
### 1. 适用场景
- 适用于老系统的不兼容新功能开发。
- 能不用就不用适配器，过多引入适配器会造成系统混乱。
- 当系统的适配器过多时，建议进行系统重构。

### 2. 角色
- 2.1. 老接口
- 2.2. 新功能
- 2.3. 适配器

### 3. 分类
- 类继承，通过继承进行功能重写。
- 类的关联，通过引入类的实例，进行包装。
    
    
## 二、组合模式
### 1. 使用场景
- 1.1. 存在树状的数据结构

  
### 2. 组成要素
- 2.1 基本原属性
- 2.2 下一级属性是当前类型
    
## 三、装饰器模式
### 1. 使用场景
- 1.1 针对已有某个方法做功能增强，包括不同功能组合
- 1.2 新增的组合功能是有序的
      
### 2. 包含角色
- 2.1 抽象的功能拦截方法
- 2.2 基础实现类
- 2.3 装饰类，包含基础实现类的实例，并实现抽象功能拦截方法
 
## 四、门面模式
### 1. 使用场景
- 1.1 服务提供的方法功能实现过于复杂
- 1.2 服务提供的方法功能实现多种多样
- 1.3 对客户端屏蔽实现方法的复杂。工厂模式在于屏蔽创建对象的复杂

### 2. 包含角色
- 2.1 服务端抽象的功能方法
- 2.2 服务端功能方法具体复杂实现
- 2.3 客户端

### 3. 常见使用例子
- 3.1 JDBC接口及其不同数据库的JDBC驱动实现
    
## 五、工厂模式
- 1.创建复杂对象，屏蔽复杂过程
- 2.根据不同参数，获取具有相同接口对象的实例

## 六、抽象工厂模式
### 1. 系统中有多个产品族，而系统一次只可能消费其中一族产品。
### 2. 抽象工厂模式的各个角色（和工厂方法的如出一辙）：
- 2.1.  抽象工厂角色： 这是工厂方法模式的核心，它与应用程序无关。是具体工厂角色必须实现的接口或者必须继承的父类。在 java 中它由抽象类或者接口来实现。
- 2.2.  具体工厂角色：它含有和具体业务逻辑有关的代码。由应用程序调用以创建对应的具体产品的对象。在 java 中它由具体的类来实现。
- 2.3.  抽象产品角色：它是具体产品继承的父类或者是实现的接口。在 java 中一般有抽象类或者接口来实现。
- 2.4.  具体产品角色：具体工厂角色所创建的对象就是此角色的实例。在 java 中由具体的类来实现。

## 七、单例模式
- 1.饿汉模式
  - a. 私有构造方法
  - b. 静态获取实例方法
  - c. 静态实例，在类实例化或加载的时候进行初始化
  
- 2.懒汉模式
  - a. 私有构造方法
  - b. 静态获取实例方法
  - c. 静态实例，在第一次调用获取实例方法的时候进行初始化
  
- 3.枚举
    
## 八、观察者模式
### 1.要素
- 1.观察者
- 2.被观察者
- 3.观察主题
	
### 2.关系

被观察者记录所有观察者，当观察主题出现时，轮询进行通知。
		
## 九、订阅者模式

>可以理解为观察者模式的升级模式，对观察者模式进行解耦。

### 1.要素
- a.发布者
- b.订阅者
- c.消息
- d.消息转发器（消息总线）
		
### 2.关系
- a.发布者初始化后向消息总线进行注册
- b.订阅者初始化 后想消息总线进行订阅
- c.消息总线负责维护发布者和订阅者之间的关系，当有发布者发布信息时，转发给订阅者。    