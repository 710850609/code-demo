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
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;

/**
 * 根据不同调用场景进行分组校验
 * 如： 新增时不需要校验记录id，更新时需要校验记录id不为空
 **/
@Slf4j
@RestController
@RequestMapping("/accounts4")
public class Account4Service {

    @PostMapping("/add")
    public Account add(@Validated({Account.Add.class, Default.class}) @RequestBody Account account) {
        log.info("Account4Service.add: {}", account);
        return account;
    }

    @PostMapping("/update")
    public Account update(@Validated({Account.Update.class, Default.class}) @RequestBody Account account) {
        log.info("Account4Service.update:{}", account);
        return account;
    }

}

