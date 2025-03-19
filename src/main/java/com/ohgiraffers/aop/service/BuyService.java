package com.ohgiraffers.aop.service;

import com.ohgiraffers.aop.dto.ProductDTO;
import com.ohgiraffers.aop.repository.ProductDAO;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuyService {

    private final ProductDAO productDAO;

    @Autowired
    public BuyService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public Map<Long, ProductDTO> butItems() {
        return productDAO.getItems();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buyItem(Long id, int count) {
        System.out.println("아이템을 구매하는 중...");

        ProductDTO item = productDAO.getItem(id);

        if (item.getItemCount() < count) {
            throw new RuntimeException("남은 상품의 수가 적습니다!");
        }

        item.buyItem(count);
        productDAO.updateItem(id, item);
        System.out.println("구매 완료");
    }

}