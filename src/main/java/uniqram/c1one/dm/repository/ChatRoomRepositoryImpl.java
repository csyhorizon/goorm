package uniqram.c1one.dm.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import uniqram.c1one.dm.entity.ChatRoom;
import uniqram.c1one.dm.entity.QChatRoom;
import uniqram.c1one.dm.entity.QChatRoomMember;
import uniqram.c1one.user.entity.QUsers;

import java.util.List;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatRoom> findChatRoomsWithMembersByUserId(Long userId) {
        QChatRoom chatRoom = QChatRoom.chatRoom;
        QChatRoomMember member = QChatRoomMember.chatRoomMember;
        QUsers users = QUsers.users;

        return queryFactory
                .selectFrom(chatRoom)
                .join(chatRoom.members, member).fetchJoin()
                .join(member.user, users).fetchJoin()
                .where(member.user.id.eq(userId))
                .distinct()
                .fetch();
    }
}
