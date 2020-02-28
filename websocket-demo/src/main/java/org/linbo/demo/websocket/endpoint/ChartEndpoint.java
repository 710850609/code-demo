package org.linbo.demo.websocket.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Slf4j
@Component
@ServerEndpoint("/chart")
public class ChartEndpoint {

    private static Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        log.info("onMessage");
        log.info(message);
        session.getBasicRemote().sendText("收到：" + message);
    }

    @OnOpen
    public void onOpen(Session session) {
        log.info("新连接");
        sessions.put(session.getId(), session);
        String msg = String.format("当前在线%d人", sessions.size());
        session.getAsyncRemote().sendText(msg);
        sendExclude(session.getId() + "上线", Arrays.asList(session.getId()));
    }

    @OnClose
    public void onClose(Session session) {
        log.info("关闭连接");
        sessions.remove(session.getId());
    }

    @OnError
    public void onError(Session session, Throwable t) {
        log.info("onError");
    }

    private void sendFor(String message, List<String> sessionIds) {
        sessions.forEach((id, session) -> {
            if (sessionIds != null && sessionIds.contains(id)) {
                session.getAsyncRemote().sendText(message);
            }
        });
    }

    private void sendExclude(String message, List<String> ids) {
        sessions.forEach((id, session) -> {
            if (ids == null || !ids.contains(id)) {
                session.getAsyncRemote().sendText(message);
            }
        });
    }

    private void sendAll(String message) {
        sendExclude(message, null);
    }

}
