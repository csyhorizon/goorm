package uniqram.c1one.dm.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import uniqram.c1one.dm.entity.ChatMessage;
import uniqram.c1one.dm.entity.QChatMessage;
import uniqram.c1one.user.entity.QUsers;

import java.util.List;

@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatMessage> findChatMessagesWithSenderByChatRoomId(Long chatRoomId, int offset, int limit) {
        QChatMessage chatMessage = QChatMessage.chatMessage;
        QUsers sender = QUsers.users;

        return queryFactory
                .selectFrom(chatMessage)
                .join(chatMessage.user, sender).fetchJoin()
                .where(chatMessage.chatRoom.id.eq(chatRoomId))
                .orderBy(chatMessage.createdAt.asc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

}
