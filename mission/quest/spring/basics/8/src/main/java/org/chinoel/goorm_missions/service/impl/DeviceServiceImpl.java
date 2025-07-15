package org.chinoel.goorm_missions.service.impl;

import org.chinoel.goorm_missions.service.AbstractDeviceService;

public class DeviceServiceImpl extends AbstractDeviceService {

    public DeviceServiceImpl(String boardName, int systemNo) {
        super(boardName, systemNo);
    }

    @Override
    public void init() {
        super.init();
        System.out.println("모바일 장비 시작");
        System.out.println("메모리 점검 중...");
    }

    @Override
    public void destroy() {
        System.out.println("실행 중인 프로그램 종료");
        super.destroy();
    }
}
