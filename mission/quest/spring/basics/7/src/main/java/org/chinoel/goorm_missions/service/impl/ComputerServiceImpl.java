package org.chinoel.goorm_missions.service.impl;

import org.chinoel.goorm_missions.service.AbstractDeviceService;

public class ComputerServiceImpl extends AbstractDeviceService {

    public ComputerServiceImpl(String boardName, int systemNo) {
        super(boardName, systemNo);
    }

    @Override
    public void init() {
        super.init();
        System.out.println("컴퓨터 부팅 시퀀스 시작");
        System.out.println("메모리 점검 중...");
    }

    @Override
    public void destroy() {
        System.out.println("실행 중인 프로그램 종료");
        super.destroy();
    }
}
