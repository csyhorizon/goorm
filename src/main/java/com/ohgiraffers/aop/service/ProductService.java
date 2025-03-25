package com.ohgiraffers.aop.service;

import com.ohgiraffers.aop.dto.ProductDTO;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Transactional
    public void buyItem(Long productId, int quantity) {
        // 롤백 체크하려고 체크 우선이 아닌 감소 먼저 했습니다.

        // 이론이 맞다면, 아이템을 DB에서 감소시키고 이를 업데이트합니다.
        // 그러나 재고는 마이너스이기에 해당 로직은 롤백처리되야합니다.
        // 따라서 분명 체크 로직이 동작하기 전 감소를 진행했으나 이는 처리되지 않습니다.

        ProductDTO product = productDAO.findById(productId);

        product.setItemCount(product.getItemCount() - quantity);
        productDAO.update(product);

        if (product.getItemCount() < 0) {
            throw new RuntimeException("재고가 부족합니다!");
        }
    }

    public List<ProductDTO> getAllProducts() {
        return productDAO.findAll();
    }
}