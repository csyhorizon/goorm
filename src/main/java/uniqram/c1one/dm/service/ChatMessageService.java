package uniqram.c1one.dm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.dm.dto.ChatMessageList;
import uniqram.c1one.dm.dto.ChatMessageRequeset;
import uniqram.c1one.dm.dto.ChatMessageResponse;
import uniqram.c1one.dm.entity.ChatMessage;
import uniqram.c1one.dm.entity.ChatRoom;
import uniqram.c1one.dm.exception.DmErrorCode;
import uniqram.c1one.dm.exception.DmException;
import uniqram.c1one.dm.repository.ChatMessageRepository;
import uniqram.c1one.dm.repository.ChatRoomRepository;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatMessageResponse sendMessage(Long chatRoomId, Long senderId, ChatMessageRequeset requeset) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new DmException(DmErrorCode.CHATROOM_NOT_FOUND));

        Users user = userRepository.findById(senderId)
                .orElseThrow(() -> new DmException(DmErrorCode.USER_NOT_FOUND));

        ChatMessage msg = ChatMessage.builder()
                .chatRoom(chatRoom)
                .user(user)
                .message(requeset.getMessage())
                .isRead(false)
                .build();

        chatMessageRepository.save(msg);

        return ChatMessageResponse.builder()
                .chatMessageId(msg.getId())
                .senderId(senderId)
                .message(msg.getMessage())
                .createdAt(msg.getCreatedAt())
                .isRead(false)
                .build();
    }

    @Transactional(readOnly = true)
    public List<ChatMessageList> getChatMessages(Long chatRoomId, int offset, int limit) {
        List<ChatMessage> messages = chatMessageRepository.findChatMessagesWithSenderByChatRoomId(chatRoomId, offset, limit);

        return messages.stream()
                .map(msg -> ChatMessageList.builder()
                        .messageId(msg.getId())
                        .senderId(msg.getUser().getId())
                        .senderName(msg.getUser().getUsername())
                        .message(msg.getMessage())
                        .createdAt(msg.getCreatedAt())
                        .isRead(msg.isRead())
                        .build())
                .collect(Collectors.toList());
    }

}
