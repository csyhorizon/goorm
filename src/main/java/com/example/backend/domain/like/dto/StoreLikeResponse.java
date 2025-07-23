package com.example.backend.domain.like.dto;

public record StoreLikeResponse(
    boolean becomeLike
) {
    public static StoreLikeResponse pressStoreLike() {
        return new StoreLikeResponse(true);
    }
    public static StoreLikeResponse cancleStoreLike(){
        return new StoreLikeResponse(false);
    }
}
