package com.websocket.chat.Like;

import com.websocket.chat.Entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {
    Optional<Like> findHeartByMessageAndSender(ChatMessage message, String sender);

}
