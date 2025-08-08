package com.example.backend.domain.like.service;

import com.example.backend.domain.like.dto.StoreLikeRequest;
import com.example.backend.domain.like.dto.StoreLikeResponse;
import com.example.backend.domain.like.entity.StoreLike;
import com.example.backend.domain.like.repository.StoreLikeRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.store.dto.StoreResponse;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreLikeService {
    private final StoreLikeRepository storeLikeRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    public StoreLikeResponse likeStore(Long storeId, Long memberId){
        Member member = memberRepository.findOrThrow(memberId);
        Store store = storeRepository.findOrThrow(storeId);
        Optional<StoreLike> storeLike = storeLikeRepository.findByMemberAndStore(member,store);
        if(storeLike.isPresent()){
            storeLikeRepository.delete(storeLike.get());
            return StoreLikeResponse.cancleStoreLike();
        }
        if(storeLike.isEmpty()){
            storeLikeRepository.save(new StoreLike(member, store));
            return StoreLikeResponse.pressStoreLike();
        }
        throw new IllegalStateException("좋아요 처리 실패");
    }

}
