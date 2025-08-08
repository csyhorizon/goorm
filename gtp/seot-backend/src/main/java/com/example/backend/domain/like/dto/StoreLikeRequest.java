package com.example.backend.domain.like.dto;


import com.example.backend.domain.like.entity.StoreLike;
import org.springframework.web.bind.annotation.GetMapping;

public record StoreLikeRequest (
        Long storeId
){
}
