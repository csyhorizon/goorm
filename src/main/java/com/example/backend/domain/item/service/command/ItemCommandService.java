package com.example.backend.domain.item.service.command;

import com.example.backend.domain.item.dto.ItemCreateRequest;
import com.example.backend.domain.item.dto.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemCommandService {
    private final ItemCreateService itemCreateService;
    private final ItemDeleteService itemDeleteService;

    public ItemResponse save(Long memberId, Long storeId, ItemCreateRequest itemCreateRequest) {
        return itemCreateService.save(memberId, storeId, itemCreateRequest);
    }

    public void delete(Long memberId, Long storeId, Long itemId) {
        itemDeleteService.deleteItem(memberId, storeId, itemId);
    }
}
