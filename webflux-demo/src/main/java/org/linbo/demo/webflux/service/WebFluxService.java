package org.linbo.demo.webflux.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * @author LinBo
 * @date 2019/12/25 21:04
 */
//@RestController
public class WebFluxService {

    @GetMapping("")
    public Flux<String> hello() {
        Flux<String> stringFlux = Flux.fromArray(new String[]{"hello", "flux"});
        stringFlux = Flux.never();
        return stringFlux;
    }

    @GetMapping("/flux")
    public Mono<String> getString(String str) {
        return Mono.just(str);
    }
}
