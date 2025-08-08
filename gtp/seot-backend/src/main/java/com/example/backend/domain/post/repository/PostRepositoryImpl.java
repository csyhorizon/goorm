package com.example.backend.domain.post.repository;

import com.example.backend.domain.post.dto.PostResponse;
import com.example.backend.domain.post.entity.Post;
import com.example.backend.domain.post.entity.PostMedia;
import com.example.backend.domain.post.entity.QPost;
import com.example.backend.domain.post.entity.QPostMedia;
import com.example.backend.domain.store.entity.QStore;
import com.example.backend.domain.store.entity.Store;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostResponse> findAllRecentPosts(Pageable pageable) {
        QPost post = QPost.post;
        QPostMedia postMedia = QPostMedia.postMedia;
        QStore store = QStore.store;

        List<Tuple> results = queryFactory
                .select(post, postMedia, store)
                .from(post)
                .leftJoin(post.store, store).fetchJoin()
                .leftJoin(post.mediaList, postMedia)
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Map<Long, List<Tuple>> postIdToTuples = results.stream()
                .collect(Collectors.groupingBy(tuple -> tuple.get(post).getId()));

        List<PostResponse> content = postIdToTuples.values().stream()
                .map(tupleList -> {
                    Tuple first = tupleList.get(0);
                    Post p = first.get(post);
                    Store s = first.get(store);
                    List<String> mediaUrls = tupleList.stream()
                            .map(t -> {
                                PostMedia pm = t.get(postMedia);
                                return pm != null ? pm.getMediaUrl() : null;
                            })
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    return PostResponse.of(p, mediaUrls, s);
                })
                .toList();

        Long total = queryFactory
                .select(post.count())
                .from(post)
                .fetchOne();

        long totalCount = (total != null) ? total : 0L;

        return new PageImpl<>(content, pageable, totalCount);

    }
}
