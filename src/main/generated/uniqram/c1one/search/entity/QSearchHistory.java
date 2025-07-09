package uniqram.c1one.search.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSearchHistory is a Querydsl query type for SearchHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSearchHistory extends EntityPathBase<SearchHistory> {

    private static final long serialVersionUID = -1636394176L;

    public static final QSearchHistory searchHistory = new QSearchHistory("searchHistory");

    public final uniqram.c1one.global.QBaseEntity _super = new uniqram.c1one.global.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath searchKeyword = createString("searchKeyword");

    public final NumberPath<Long> userid = createNumber("userid", Long.class);

    public QSearchHistory(String variable) {
        super(SearchHistory.class, forVariable(variable));
    }

    public QSearchHistory(Path<? extends SearchHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSearchHistory(PathMetadata metadata) {
        super(SearchHistory.class, metadata);
    }

}

