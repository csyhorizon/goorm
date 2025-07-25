package com.example.backend.domain.global.router;

import com.example.backend.domain.global.config.DataSourceContextHolder;
import com.example.backend.domain.global.config.DataSourceType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.get() == null ? DataSourceType.WRITE : DataSourceContextHolder.get();
    }
}
