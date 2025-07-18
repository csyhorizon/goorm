package com.example.backend.domain.store.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import com.example.backend.domain.user.entity.Users;
import com.example.backend.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.backend.support.annotation.ServiceTest;
import com.example.backend.support.fixture.UsersFixture;
import com.example.backend.support.fixture.StoreFixture;

@ServiceTest
public class StoreServiceTest {
    @Autowired
    StoreService storeService;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void 가게를_저장할_수_있다() {
        Users user = UsersFixture.김회원();
        userRepository.save(user);
        Store store = StoreFixture.과일가게(user);
        storeRepository.save(store);

        assertThat(storeRepository.findAll()).hasSize(1);
    }

    @Test
    void 가게를_삭제할_수_있다() {
        Users user = UsersFixture.김회원();
        userRepository.save(user);
        Store store = StoreFixture.과일가게(user);
        storeRepository.save(store);

        storeRepository.delete(store);

        assertThat(storeRepository.findAll()).hasSize(0);
    }
}
