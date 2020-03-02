package org.linbo.demo.websocket.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.linbo.demo.websocket.entity.Cmd;
import org.linbo.demo.websocket.entity.Message;
import org.linbo.demo.websocket.entity.User;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint(value = "/chart")
public class ChartEndpoint {

    /** 会话分配的用户名 */
    private static LinkedList<String> userNames = new LinkedList<>(Arrays.asList("张飞", "小皮", "铁蛋", "诸葛", "小茜"));
    /** sessionId - session */
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();
    /** sessionId - userName */
    private static Map<String, String> users = new ConcurrentHashMap<>();

    private static ObjectMapper objectMapper = new ObjectMapper();

    @OnMessage
    public void onMessage(Session session, String data) throws IOException {
        log.info("onMessage");
        log.info(data);
        Message message = objectMapper.readValue(data, Message.class);
        message.setDate(new Date());
        sendAll(message);
    }

    @OnOpen
    public void onOpen(Session session) {
        log.info("新连接 {}", this);
        String name = userNames.pollLast();
        if (name == null) {
            log.error("已经没有用户名分配了");
            return;
        }
        sessions.put(session.getId(), session);
        users.put(session.getId(), name);
        // 返回当前会话分配的信息
        Message sessionMsg = Message.builder()
                .cmd(Cmd.LOGIN)
                .session(new User(session.getId(), name))
                .message("当前分配用户名： 【" + name + "】")
                .date(new Date())
                .build();
        session.getAsyncRemote().sendText(sessionMsg.toJsonString());
        // 上线通知
        Message loginMsg = Message.builder()
                .cmd(Cmd.NOTIFIY)
                .message("【" + name + "】 上线")
                .date(new Date())
                .build();
        sendExclude(loginMsg, Arrays.asList(session.getId()));
        log.info("users: {}", users);
    }

    @OnClose
    public void onClose(Session session) {
        log.info("关闭连接");
        sessions.remove(session.getId());
        String name = users.get(session.getId());
        userNames.push(name);
        users.remove(session.getId());
        Message message = Message.builder()
                .cmd(Cmd.NOTIFIY)
                .message("【" + name + "】下线")
                .build();

        sendExclude(message, Arrays.asList(session.getId()));
        log.info("users: {}", users);
    }

    @OnError
    public void onError(Session session, Throwable t) {
        log.error("onError", t);
    }

    /** 指定sessionId发送 */
    private void sendFor(Message message, List<String> sessionId) {
        sessions.forEach((id, session) -> {
            if (sessionId != null && sessionId.contains(id)) {
                try {
                    log.info("给【{}】发送： 【{}】", users.get(id), objectMapper.writeValueAsString(message));
                    session.getAsyncRemote().sendText(objectMapper.writeValueAsString(message));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /** 发送全部，除指定sessionId外 */
    private void sendExclude(Message message, List<String> sessionId) {
        sessions.forEach((id, session) -> {
            if (sessionId == null || !sessionId.contains(id)) {
                log.info("给【{}】发送： 【{}】", users.get(id), message);
                session.getAsyncRemote().sendText(message.toJsonString());
            }
        });
    }

    /** 发送全部 */
    private void sendAll(Message message) {
        sendExclude(message, null);
    }

}
