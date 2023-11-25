package com.websocket.chat.Like;

import com.websocket.chat.Entity.ChatMessage;
import com.websocket.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final ChatMessageRepository chatMessageRepository;

    public void heart(LikDto likDto) {
        ChatMessage chatMessage = null;

        if (likDto.getMessageId() != null) {
            chatMessage = chatMessageRepository.findById(Long.valueOf(likDto.getMessageId()))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Message ID"));
        } else {
            log.error("Message ID is null");
        }

        Like like = Like.builder()
                .sender(likDto.getSender())
                .message(chatMessage)
                .build();

        likeRepository.save(like);
        log.info("Like saved: {}", like);
    }

    public void unHeart(LikDto likDto) {
        Optional<Like> heartOpt = findHeartWithMessageAndSender(likDto);
        heartOpt.ifPresent(likeRepository::delete);
    }

    public Optional<Like> findHeartWithMessageAndSender(LikDto likDto) {
        if (likDto.getMessageId() == null) {
            log.error("Message ID is null");
            return Optional.empty();
        }

        return likeRepository.findHeartByMessageAndSender(
                chatMessageRepository.findById(Long.valueOf(likDto.getMessageId())).orElse(null),
                likDto.getSender()
        );
    }
}





