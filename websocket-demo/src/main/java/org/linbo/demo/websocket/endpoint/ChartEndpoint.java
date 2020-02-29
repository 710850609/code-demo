package org.linbo.demo.websocket.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.linbo.demo.websocket.entity.ChartMessage;
import org.linbo.demo.websocket.entity.Cmd;
import org.linbo.demo.websocket.entity.Message;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint(value = "/chart")
public class ChartEndpoint {

    private static Map<String, Session> sessions = new ConcurrentHashMap<>();

    private static ObjectMapper objectMapper = new ObjectMapper();

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        log.info("onMessage");
        log.info(message);
        ChartMessage chartMessage = objectMapper.readValue(message, ChartMessage.class);

        sendFor(chartMessage, Arrays.asList(chartMessage.getTo()));

//        String temp = chartMessage.getFrom();
//        chartMessage.setFrom(chartMessage.getTo());
//        chartMessage.setTo(temp);
//        chartMessage.setMessage("收到了 " + chartMessage.getMessage());
//        session.getBasicRemote().sendText(objectMapper.writeValueAsString(chartMessage));
    }

    @OnOpen
    public void onOpen(Session session) {
        log.info("新连接");
        sessions.put(session.getId(), session);
        Message message = Message.builder()
                .sessionId(session.getId())
                .cmd(Cmd.LOGIN)
                .build();
        session.getAsyncRemote().sendText(message.toJsonString());
        sendExclude(message, Arrays.asList(session.getId()));
    }

    @OnClose
    public void onClose(Session session) {
        log.info("关闭连接");
        sessions.remove(session.getId());
        Message message = Message.builder()
                .sessionId(session.getId())
                .cmd(Cmd.LOGOUT)
                .build();
        sendExclude(message, Arrays.asList(session.getId()));
    }

    @OnError
    public void onError(Session session, Throwable t) {
        log.info("onError");
    }

    private void sendFor(ChartMessage message, List<String> uids) {
        sessions.forEach((id, session) -> {
            if (uids != null && uids.contains(id)) {
                try {
                    log.info("给【{}】发送： 【{}】", id, objectMapper.writeValueAsString(message));
                    session.getAsyncRemote().sendText(objectMapper.writeValueAsString(message));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendExclude(Message message, List<String> ids) {
        sessions.forEach((id, session) -> {
            if (ids == null || !ids.contains(id)) {
                log.info("给【{}】发送： 【{}】", id, message);
                session.getAsyncRemote().sendText(message.toJsonString());
            }
        });
    }


}
