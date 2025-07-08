package uniqram.c1one.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsers is a Querydsl query type for Users
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsers extends EntityPathBase<Users> {

    private static final long serialVersionUID = -957013895L;

    public static final QUsers users = new QUsers("users");

    public final uniqram.c1one.global.QBaseEntity _super = new uniqram.c1one.global.QBaseEntity(this);

    public final BooleanPath blacklisted = createBoolean("blacklisted");

    public final ListPath<uniqram.c1one.comment.entity.Comment, uniqram.c1one.comment.entity.QComment> comments = this.<uniqram.c1one.comment.entity.Comment, uniqram.c1one.comment.entity.QComment>createList("comments", uniqram.c1one.comment.entity.Comment.class, uniqram.c1one.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath password = createString("password");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath username = createString("username");

    public QUsers(String variable) {
        super(Users.class, forVariable(variable));
    }

    public QUsers(Path<? extends Users> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsers(PathMetadata metadata) {
        super(Users.class, metadata);
    }

}

