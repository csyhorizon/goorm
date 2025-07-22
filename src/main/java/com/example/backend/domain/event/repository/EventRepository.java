package com.example.backend.domain.event.repository;

import com.example.backend.domain.event.entity.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByStoreId(Long storeId);
}
