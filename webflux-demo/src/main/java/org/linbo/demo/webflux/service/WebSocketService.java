package org.linbo.demo.webflux.service;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * @author LinBo
 * @date 2019/12/29 21:36
 */
@Component
public class WebSocketService implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(
                webSocketSession.receive()
                        .map(msg -> webSocketSession.textMessage(
                                "服务端返回：小明， -> " + msg.getPayloadAsText())));
    }
}
