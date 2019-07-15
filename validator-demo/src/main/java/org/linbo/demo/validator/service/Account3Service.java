package org.linbo.demo.validator.service;

import lombok.extern.slf4j.Slf4j;
import org.linbo.demo.validator.bean.Account;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 校验基础类型的方法入参
 **/
@Slf4j
@RestController
@Validated
@RequestMapping("/accounts3")
public class Account3Service {

    @PostMapping("/add1")
    public Account add1(@Valid @RequestBody Account account) {
        log.info("Account3Service.Service.add1: {}", account);
        return account;
    }

    @PostMapping("/add2")
    public Account add2(@Valid @RequestBody Account account, BindingResult bindingResult) {
        log.info("新增账户: {}", account);
        log.info("Account3Service.add2:bindingResult:{}", bindingResult);
        return account;
    }

    @PostMapping("/update")
    public String update(@NotBlank String id) {
        log.info("Account3Service.update: {}", id);
        return id;
    }

    @PostMapping("/update2")
//    @Validated
    public String update2(@NotBlank String id, @NotBlank String name) {
        log.info("Account3Service.update2: {}, {}", id, name);
        return id;
    }

}

