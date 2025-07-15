package org.chinoel.goorm_missions.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public abstract class AbstractDeviceService implements DeviceService {
    private String boardName;
    private int systemNo;

    private AbstractDeviceService() {}

    public AbstractDeviceService(String boardName, int systemNo) {
        this.boardName = boardName;
        this.systemNo = systemNo;
    }

    @Override
    @PostConstruct
    public void init() {
        System.out.println(systemNo + "번 코드 동작...");
        System.out.println(boardName + "보드로 활성화");
    }

    @Override
    @PreDestroy
    public void destroy() {
        System.out.println("시스템 종료 중...");
        System.out.println(boardName + "보드 비활성화");
    }
}
