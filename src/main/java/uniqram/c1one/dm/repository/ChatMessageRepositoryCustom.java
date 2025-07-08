package uniqram.c1one.dm.repository;

import uniqram.c1one.dm.entity.ChatMessage;

import java.util.List;

public interface ChatMessageRepositoryCustom {
    List<ChatMessage> findChatMessagesWithSenderByChatRoomId(Long chatRoomId, int offset, int limit);
}
