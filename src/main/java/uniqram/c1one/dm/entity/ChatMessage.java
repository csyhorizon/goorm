package uniqram.c1one.dm.entity;

import jakarta.persistence.*;
import lombok.*;
import uniqram.c1one.global.BaseEntity;
import uniqram.c1one.user.entity.Users;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(nullable = false)
    private String message;

    @Builder.Default
    private boolean isRead = false;

    public void update(String message) {
        this.message = message;
    }
}
