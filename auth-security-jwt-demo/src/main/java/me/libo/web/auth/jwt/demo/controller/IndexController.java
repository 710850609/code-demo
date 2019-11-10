package me.libo.web.auth.jwt.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author LinBo
 * @date 2019/11/10 11:35
 */
@Slf4j
@RestController
public class IndexController {

    @GetMapping("/")
    public String index(HttpSession session) {
        log.info("SessionId: {}", session.getId());
        return "hello index";
    }
}
