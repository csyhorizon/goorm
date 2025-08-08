package com.example.backend.domain.item.repository;

import com.example.backend.domain.item.entity.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByStoreId(Long storeId);
}
