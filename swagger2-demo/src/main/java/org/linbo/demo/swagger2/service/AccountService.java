package org.linbo.demo.swagger2.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.linbo.demo.swagger2.bean.Account;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author LinBo
 * @date 2019-07-11 16:06
 */
@Slf4j
@Api(value = "账户服务")
@RestController
@RequestMapping("/accounts")
public class AccountService {

    /**
     * 新增account
     * @Author LinBo
     * @Date 2019/7/11 16:08
     * @param account
     * @return {@link Account}
     **/
    @ApiOperation(value = "新增账户", notes = "注意有些参数非空")
    @PostMapping("/")
    public Account add(@RequestBody Account account) {
        log.info("新增账户: {}", account);
        return account;
    }

    /**
     * 禁用account
     * @Author LinBo
     * @Date 2019/7/11 16:08
     * @param id
     * @return {@link Account}
     **/
    @ApiOperation(value = "禁用账户")
    @ApiImplicitParam(name = "id", value = "账户id", required = true, dataType = "String")
    @PostMapping("/{id}")
    public String disable(@PathVariable("id") String id) {
        log.info("禁用账户, id={}", id);
        return null;
    }
}
