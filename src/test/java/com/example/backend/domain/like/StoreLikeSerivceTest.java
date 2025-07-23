package com.example.backend.domain.like;

import com.example.backend.domain.like.dto.StoreLikeResponse;
import com.example.backend.domain.like.repository.StoreLikeRepository;
import com.example.backend.domain.like.service.StoreLikeService;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import com.example.backend.support.annotation.ServiceTest;
import com.example.backend.support.fixture.MemberFixture;
import com.example.backend.support.fixture.StoreFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
public class StoreLikeSerivceTest {
    @Autowired
    StoreLikeService storeLikeService;
    @Autowired
    StoreLikeRepository storeLikeRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    MemberRepository memberRepository;
    @Test
    void 가게에_좋아요_좋아요취소_스위칭(){
        Member member = MemberFixture.김회원();
        memberRepository.save(member);
        Store store = StoreFixture.과일가게(member);
        storeRepository.save(store);
        Assertions.assertThat(storeLikeRepository.findByMemberAndStore(member,store)).isEmpty();
        StoreLikeResponse storeLikeResponse = storeLikeService.likeStore(member.getId(), store.getId());

        Assertions.assertThat(storeLikeResponse.becomeLike()).isEqualTo(true);
        StoreLikeResponse storeLikeResponse2 = storeLikeService.likeStore(member.getId(), store.getId());
        Assertions.assertThat(storeLikeResponse2.becomeLike()).isEqualTo(false);

    }
}
