package com.ohgiraffers.aop.service;

import com.ohgiraffers.aop.dto.ProductDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ProductDTO findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> new ProductDTO(
                            rs.getLong("id"),
                            rs.getString("item_name"),
                            rs.getInt("price"),
                            rs.getInt("item_count")
                    ), id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("상품을 찾을 수 없습니다 : " + id);
        }
    }

    public List<ProductDTO> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ProductDTO(
                        rs.getLong("id"),
                        rs.getString("item_name"),
                        rs.getInt("price"),
                        rs.getInt("item_count")
                )
        );
    }

    public void update(ProductDTO product) {
        String sql = "UPDATE products SET item_name = ?, price = ?, item_count = ? WHERE id = ?";
        int affected = jdbcTemplate.update(sql,
                product.getItemName(),
                product.getPrice(),
                product.getItemCount(),
                product.getId()
        );
        if (affected == 0) {
            throw new RuntimeException("상품 업데이트 실패: " + product.getId());
        }
    }
}
