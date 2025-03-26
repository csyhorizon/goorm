package com.ohgiraffers.bean.section01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        System.out.println("BeforeEacch : 테스트를 시작합니다.");
    }


    @Test
    @DisplayName("신기하지")
    void clear() {
        assertEquals("test", "test");
    }

}