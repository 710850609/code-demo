package org.linbo.demo.aop.service;

import lombok.extern.slf4j.Slf4j;
import org.linbo.demo.aop.vo.Account;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户服务
 **/
@RestController
@RequestMapping("/accounts")
@Slf4j
public class AccountService {

    /**
     * 正常新增
     **/
    @PostMapping("/add")
    public Account add(@RequestBody Account account) {
        log.info("AccountService.add ：{}", account);
        return account;
    }

    /**
     * 新增时模拟发送异常
     **/
    @PostMapping("/addWithError")
    public Account addWithError(@RequestBody Account account) {
        log.info("AccountService.addWithError ：{}", account);
        throw new RuntimeException("新增account出现了异常");
    }
}
