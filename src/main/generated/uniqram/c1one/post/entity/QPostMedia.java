package uniqram.c1one.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostMedia is a Querydsl query type for PostMedia
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostMedia extends EntityPathBase<PostMedia> {

    private static final long serialVersionUID = 1513960672L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostMedia postMedia = new QPostMedia("postMedia");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath mediaUrl = createString("mediaUrl");

    public final QPost post;

    public QPostMedia(String variable) {
        this(PostMedia.class, forVariable(variable), INITS);
    }

    public QPostMedia(Path<? extends PostMedia> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostMedia(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostMedia(PathMetadata metadata, PathInits inits) {
        this(PostMedia.class, metadata, inits);
    }

    public QPostMedia(Class<? extends PostMedia> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

