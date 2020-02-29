package org.linbo.demo.aop.crypt.service;

import org.linbo.demo.aop.crypt.EncryptRequest;
import org.linbo.demo.aop.crypt.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class UserRequestService {

    @EncryptRequest
    @GetMapping("/list")
    public ResponseEntity list(String name, String password) {
        Map<String, String> map = new HashMap<>(2);
        map.put("name", name);
        map.put("password", password);
        return ResponseEntity.ok(map);
    }

    @EncryptRequest
    @PostMapping("/add1")
    public ResponseEntity post(String name, String password) {
        Map<String, String> map = new HashMap<>(2);
        map.put("name", name);
        map.put("password", password);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/add2")
    public ResponseEntity post(@RequestBody User user) {
        return ResponseEntity.ok(user);
    }


}
