package uniqram.c1one.dm.repository;

import uniqram.c1one.dm.entity.ChatRoom;

import java.util.List;

public interface ChatRoomRepositoryCustom {
    List<ChatRoom> findChatRoomsWithMembersByUserId(Long userId);
}
