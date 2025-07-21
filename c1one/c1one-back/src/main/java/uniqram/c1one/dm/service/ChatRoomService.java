package uniqram.c1one.dm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.dm.dto.*;
import uniqram.c1one.dm.entity.ChatMessage;
import uniqram.c1one.dm.entity.ChatRoom;
import uniqram.c1one.dm.entity.ChatRoomMember;
import uniqram.c1one.dm.entity.ChatRoomType;
import uniqram.c1one.dm.exception.DmErrorCode;
import uniqram.c1one.dm.exception.DmException;
import uniqram.c1one.dm.repository.ChatMessageRepository;
import uniqram.c1one.dm.repository.ChatRoomMemberRepository;
import uniqram.c1one.dm.repository.ChatRoomRepository;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatRoomCreateResponse createChatRoom(Long userId, ChatRoomCreateRequest request) {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomType(ChatRoomType.DM)
                .build();
        chatRoomRepository.save(chatRoom);

        List<Long> allUserIds = new ArrayList<>(request.getUserIds());
        allUserIds.add(userId); //본인 포함

        List<ChatRoomMember> members = allUserIds.stream().map(uid -> {
            Users user = userRepository.findById(uid)
                    .orElseThrow(() -> new DmException(DmErrorCode.USER_NOT_FOUND));
            return ChatRoomMember.builder()
                    .chatRoom(chatRoom)
                    .user(user)
                    .build();
        }).collect(Collectors.toList());

        chatRoomMemberRepository.saveAll(members);

        return ChatRoomCreateResponse.builder()
                .chatRoomId(chatRoom.getId())
                .members(members.stream()
                        .map(m -> ChatRoomCreateResponse.MemberDto.builder()
                                .userId(m.getUser().getId())
                                .username(m.getUser().getUsername())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }




    @Transactional(readOnly = true)
    public List<ChatRoomList> getMyChatRooms(Long userId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsWithMembersByUserId(userId);

        return chatRooms.stream()
                .map(room -> {
                    List<ChatRoomList.MemberDto> memberDtos = room.getMembers().stream()
                            .map(m -> ChatRoomList.MemberDto.builder()
                                    .userId(m.getUser().getId())
                                    .username(m.getUser().getUsername())
                                    .build())
                            .collect(Collectors.toList());

                    //마지막 메세지
                    Optional<ChatMessage> lastMsg = chatMessageRepository.findTopByChatRoomIdOrderByCreatedAtDesc(room.getId());

                    return ChatRoomList.builder()
                            .chatroomId(room.getId())
                            .type(room.getRoomType())
                            .createdAt(room.getCreatedAt())
                            .lastMessage(lastMsg.map(ChatMessage::getMessage).orElse(null))
                            .lastMessageAt(lastMsg.map(ChatMessage::getCreatedAt).orElse(null))
                            .members(memberDtos)
                            .build();
                })
                .collect(Collectors.toList());
    }

}
