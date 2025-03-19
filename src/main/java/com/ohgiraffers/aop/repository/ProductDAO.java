package com.ohgiraffers.aop.repository;

import com.ohgiraffers.aop.dto.ProductDTO;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAO {

    private final Map<Long, ProductDTO> itemMap;

    public ProductDAO() {
        itemMap = new HashMap<>();
        itemMap.put(1L, new ProductDTO("감자", 1000, 1));
        itemMap.put(2L, new ProductDTO("고기", 3000, 3));
    }

    public Map<Long, ProductDTO> getItems() {
        return itemMap;
    }

    public ProductDTO getItem(Long id) {
        ProductDTO returnItem = itemMap.get(id);

        if (returnItem == null) {
            throw new RuntimeException("해당하는 상품이 없습니다.");
        }

        return returnItem;
    }

    public void updateItem(Long id, ProductDTO item) {
        itemMap.put(id, item);
    }
}
