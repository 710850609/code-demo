# JSR-303 参数校验

## 1、请求参数加上符合JSR-303校验注解

## 2、请求参数前面加上 ```@javax.validation.Valid``` 注解，或是 ```@Validated``` 注解

## 3、如果请求参数列表中有 ```BindingResult```，则springmvc框架不会向外抛异常，默认代码自行处理

## 4、```@javax.validation.Valid``` 注解只对对象类型参数生效，对于单个参数，需要在类加上 ```@Validated``` 注解，已达到单参数校验支持