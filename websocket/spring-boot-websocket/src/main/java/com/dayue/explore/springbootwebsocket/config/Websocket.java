package com.dayue.explore.springbootwebsocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;

@Component
@Slf4j
public class Websocket extends TextWebSocketHandler {
    /**
     * socket 建立成功事件
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String name = (String)session.getAttributes().get("name");
        String ip = (String)session.getAttributes().get("ip");
        log.info("ip:{}", ip);
        if (!StringUtils.isEmpty(name)) {
            // 用户连接成功，放入在线用户缓存
            WsSessionManager.add(name, session);
        } else {
            throw new RuntimeException("用户登录已经失效!");
        }
    }

    /**
     * 接收消息事件
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获得客户端传来的消息
        String payload = message.getPayload();
        Object name = session.getAttributes().get("name");
        System.out.println("server 接收到 " + name + " 发送的 " + payload);
        session.sendMessage(new TextMessage("server 发送给 " + name + " 消息 " + payload + " " + LocalDateTime.now()));
    }

    /**
     * socket 断开连接时
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object token = session.getAttributes().get("name");
        if (token != null) {
            // 用户退出，移除缓存
            WsSessionManager.remove(token.toString());
        }
    }
}
