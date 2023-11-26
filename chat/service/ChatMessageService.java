package com.websocket.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
@Slf4j
public class ChatMessageService {
    private static final String MESSAGE = "CHAT_MESSAGE";

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Message> opsHashMessage;

    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        opsHashMessage = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public List<Message> findAllMsg() {
        return opsHashMessage.values(MESSAGE);
    }

}

