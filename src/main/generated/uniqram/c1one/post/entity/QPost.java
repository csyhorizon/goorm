package uniqram.c1one.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -782136988L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final uniqram.c1one.global.QBaseEntity _super = new uniqram.c1one.global.QBaseEntity(this);

    public final NumberPath<Integer> commentCount = createNumber("commentCount", Integer.class);

    public final ListPath<uniqram.c1one.comment.entity.Comment, uniqram.c1one.comment.entity.QComment> comments = this.<uniqram.c1one.comment.entity.Comment, uniqram.c1one.comment.entity.QComment>createList("comments", uniqram.c1one.comment.entity.Comment.class, uniqram.c1one.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath disableComments = createBoolean("disableComments");

    public final StringPath Hashtag = createString("Hashtag");

    public final BooleanPath hideLikeAndViewCount = createBoolean("hideLikeAndViewCount");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath location = createString("location");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final uniqram.c1one.user.entity.QUsers user;

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new uniqram.c1one.user.entity.QUsers(forProperty("user")) : null;
    }

}

