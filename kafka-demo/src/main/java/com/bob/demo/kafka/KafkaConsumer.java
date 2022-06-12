package com.bob.demo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "${app.topic.my-topic}")
    public void receive(@Payload String message,
                        @Headers MessageHeaders headers) {
        log.info("received message='{}'", message);
        headers.keySet().forEach(key -> log.info("{}: {}", key, headers.get(key)));
    }


}
