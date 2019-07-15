package org.linbo.demo.validator.service;

import lombok.extern.slf4j.Slf4j;
import org.linbo.demo.validator.bean.Account;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 *  <p>1、请求参数加上符合JSR-303校验注解</p>
 *  <p>2、请求参数前面加上 @{@link javax.validation.Valid } 注解，或是 @{@link Validated } 注解</p>
 *  <p>3、如果请求参数列表中有 {@link BindingResult }，则springmvc框架不会向外抛异常，默认代码自行处理</p>
 *  <p>4、@{@link javax.validation.Valid } 注解只对对象类型参数生效，对于单个参数，需要在类加上 @{@link Validated } 注解，已达到单参数校验支持</p>
 **/
@Slf4j
@RestController
@RequestMapping("/accounts1")
public class Account1Service {

    @PostMapping("/add1")
    public Account add1(@Valid @RequestBody Account account) {
        log.info("Account1Service.add1 : {}", account);
        return account;
    }


    @PostMapping("/add2")
    public Account add2(@Valid @RequestBody Account account, BindingResult bindingResult) {
        log.info("Account1Service.add2 : {}", account);
        log.info("AccountService.add2:bindingResult:{}", bindingResult);
        return account;
    }

}
