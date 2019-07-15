package org.linbo.demo.validator.service;

import lombok.extern.slf4j.Slf4j;
import org.linbo.demo.validator.bean.Account;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Account2Service 和 Account1Service 比较 @Validated 和 @Valid 的区别： 没有太大区别
 **/
@Slf4j
@RestController
@RequestMapping("/accounts2")
public class Account2Service {

    @PostMapping("/add1")
    public Account add1(@Validated @RequestBody Account account) {
        log.info("Account2Service.add1: {}", account);
        return account;
    }


    @PostMapping("/add2")
    public Account add2(@Validated @RequestBody Account account, BindingResult bindingResult) {
        log.info("新增账户: {}", account);
        log.info("AccountService.add2:bindingResult:{}", bindingResult);
        return account;
    }

}
