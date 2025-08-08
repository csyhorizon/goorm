package com.example.backend.support;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public class DatabaseCleaner {

    private static final String TRUNCATE_FORMAT = "TRUNCATE TABLE %s";
    private static final String ID_RESET_FORMAT = "ALTER TABLE %s ALTER COLUMN ID RESTART WITH 1";
    private static final String REFERENTIAL_FORMAT = "SET REFERENTIAL_INTEGRITY %s"; // H2에서 외래키 비활성화

    private final EntityManager entityManager;
    private final DataSource dataSource;
    private List<String> tableNames = new ArrayList<>();

    @Transactional
    public void execute() {
        entityManager.clear();
        executeTruncate();
    }

    private void executeTruncate() {
        entityManager.flush();
        entityManager.createNativeQuery(String.format(REFERENTIAL_FORMAT, "0")).executeUpdate(); // FOREIGN_KEY_CHECKS = 0
        tableNames.forEach(tableName -> {
            entityManager.createNativeQuery(String.format(TRUNCATE_FORMAT, tableName)).executeUpdate();
            entityManager.createNativeQuery(String.format(ID_RESET_FORMAT, tableName)).executeUpdate(); // AUTO_INCREMENT 리셋
        });
        entityManager.createNativeQuery(String.format(REFERENTIAL_FORMAT, "1")).executeUpdate(); // FOREIGN_KEY_CHECKS = 1
    }

    /**
     * Spring Boot 2.7.5 부터는 H2 DB의 기본 데이터베이스 테이블 값도 같이 가져옴
     */
    @PostConstruct
    public void afterPropertiesSet() {
        try (final var connection = dataSource.getConnection()) {
            final var metaData = connection.getMetaData();
            final var rs = metaData.getTables(null, null, null, new String[]{"TABLE"});

            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                if (!SystemTableName.matches(tableName)) {
                    tableNames.add(tableName);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving table names", e);
        }
    }
}
