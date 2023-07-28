package com.dayue.explore.springbootwebsocket.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;

import java.nio.charset.Charset;
import java.util.Map;

@Component
public class WebsocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("握手开始");
        // 获得请求参数
        Map<String, String> paramMap = HttpUtil.decodeParamMap(request.getURI().getQuery(), Charset.defaultCharset());
        String name = paramMap.get("name");
        if (StrUtil.isNotBlank(name)) {
            // 放入属性域
            attributes.put("name", name);
            String ip = request.getRemoteAddress().getHostString();
            attributes.put("ip", ip);
            System.out.println("用户 name " + name + " 握手成功！");
            return true;
        }
        System.out.println("用户登录已失效");
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                               WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("握手完成");
    }
}
