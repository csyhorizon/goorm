package com.example.backend.domain.like.entity;

import com.example.backend.domain.global.BaseEntity;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class StoreLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public StoreLike(Member member,Store store){
        this.member=member;
        this.store=store;
    }
}
