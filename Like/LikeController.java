package com.websocket.chat.Like;

import com.websocket.chat.Entity.ChatMessage;
import com.websocket.chat.pubsub.RedisPublisher;
import com.websocket.chat.service.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

@Slf4j
@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final LikeRepository likeRepository;
    private final RedisPublisher redisPublisher;
    private final ChatRoomService chatRoomService;

    @PostMapping("/add")
    @Operation(summary = "좋아요 추가")
    public ResponseEntity<LikDto> heart(@RequestBody @Valid LikDto likDto) {
        likeService.heart(likDto);
        return new ResponseEntity<>(likDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "좋아요 삭제")
    public ResponseEntity<LikDto> unHeart(@RequestBody @Valid LikDto likDto) {
        likeService.unHeart(likDto);
        return new ResponseEntity<>(likDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(summary = "좋아요 조회")
    public ResponseEntity<List<Like>> getAllLikes() {
        List<Like> allLikes = likeRepository.findAll();
        return new ResponseEntity<>(allLikes, HttpStatus.OK);
    }
}


