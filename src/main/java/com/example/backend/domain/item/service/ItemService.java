package com.example.backend.domain.item.service;

import com.example.backend.domain.event.entity.Event;
import com.example.backend.domain.event.entity.EventCategory;
import com.example.backend.domain.event.repository.EventRepository;
import com.example.backend.domain.item.dto.ItemCreateRequest;
import com.example.backend.domain.item.dto.ItemResponse;
import com.example.backend.domain.item.entity.Item;
import com.example.backend.domain.item.repository.ItemRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;
    private final EventRepository eventRepository;

    public ItemResponse save(Long memberId, Long storeId, ItemCreateRequest itemCreateRequest) {
        Member member = memberRepository.findOrThrow(memberId);
        Store store = storeRepository.findOrThrow(storeId);
        validateIsOwner(member, store);

        Item item = itemRepository.save(itemCreateRequest.toEntity(store));
        return ItemResponse.from(item);
    }

    private void validateIsOwner(Member member, Store store) {
        if (!Objects.equals(member.getId(), store.getOwnerId())) {
            throw new IllegalArgumentException("this member is not Owner to this store");
        }
    }

    //todo 추후 Slice로 변경 가능성 있음
    @Transactional(readOnly = true)
    public List<ItemResponse> findAllByStoreId(Long storeId) {
        Store store = storeRepository.findOrThrow(storeId);
        List<Item> itemList = itemRepository.findAllByStoreId(store.getId());

        return itemList.stream()
                .map(ItemResponse::from)
                .toList();
    }

    public void deleteItem(Long memberId, Long storeId, Long itemId) {
        Member member = memberRepository.findOrThrow(memberId);
        Store store = storeRepository.findOrThrow(storeId);
        validateIsOwner(member, store);

        itemRepository.delete(findOrThrow(itemId));
    }

    @Transactional(readOnly = true)
    public ItemResponse findById(Long itemId) {
        Item item = findOrThrow(itemId);
        return ItemResponse.from(item);
    }

    private Item findOrThrow(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("not found"));
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> getItemsWithEvent(Long storeId, Long eventId) {
        Store store = storeRepository.findOrThrow(storeId);
        List<Item> itemList = itemRepository.findAllByStoreId(store.getId());
        Event event = eventRepository.findOrThrow(eventId);

        return itemList.stream()
                .map(item -> ItemResponse.of(item, calculatePrice(item, event)))
                .toList();
    }

    private int calculatePrice(Item item, Event event) {
        if (event.getCategory().equals(EventCategory.DISCOUNT_AMOUNT)) {
            return item.getPrice() - event.getDiscountAmount();
        }

        if (event.getCategory().equals(EventCategory.DISCOUNT_PERCENTAGE)) {
            return (int) (item.getPrice() - (item.getPrice() * event.getDiscountRate()));
        }

        return item.getPrice();
    }
}
