package com.websocket.chat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

// 내장 레디스 실행

@Slf4j
@Profile("local")
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

//    @PostConstruct
//    public void redisServer() throws IOException {
//        redisServer = new RedisServer(redisPort);
//        redisServer.start();
    @PostConstruct
    public void start() {
        redisServer = RedisServer.builder()
                .port(6379)
                .setting("maxmemory 128M")
                .build();
        try {
            redisServer.start();
        }catch (Exception e){
            log.error("",e);
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
