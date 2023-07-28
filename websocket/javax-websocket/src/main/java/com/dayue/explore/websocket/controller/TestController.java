package com.dayue.explore.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @Resource
    private Websocket websocket;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/sendMsg")
    public void sendMsg(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        log.info("发送消息:{}", msg);
        stringRedisTemplate.convertAndSend("REDIS_MQ", msg);
        stringRedisTemplate.convertAndSend("REDIS_MQ1", msg);
    }
}
