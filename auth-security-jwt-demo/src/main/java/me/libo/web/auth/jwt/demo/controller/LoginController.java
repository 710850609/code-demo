package me.libo.web.auth.jwt.demo.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author LinBo
 * @date 2020/1/26 19:26
 */
@RestController
public class LoginController {

    @PostMapping("/login")
    public String login(String name) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", name);
        return Jwts.builder().setSubject(name).compact();
    }

}
