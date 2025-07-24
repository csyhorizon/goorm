package com.example.backend.domain.global.config;

public class DataSourceContextHolder {
    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();

    public static void set(DataSourceType type) {
        contextHolder.set(type);
    }

    public static DataSourceType get() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}
