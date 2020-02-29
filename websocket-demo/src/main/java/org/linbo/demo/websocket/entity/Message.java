package org.linbo.demo.websocket.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Message {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String sessionId;

    private String cmd;

    private String from;

    private String to;

    private String message;

    private Date date;

    public String toJsonString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


}
