package org.linbo.demo.validator.service;

import lombok.extern.slf4j.Slf4j;
import org.linbo.demo.validator.bean.Account;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
