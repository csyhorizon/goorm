package com.example.backend.domain.store.service.command;

import com.example.backend.domain.store.dto.CoordinateRequest;
import com.example.backend.domain.store.dto.StoreCreateRequest;
import com.example.backend.domain.store.dto.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreCommandService {
    private final StoreCreateService storeCreateService;
    private final StoreDeleteService storeDeleteService;
    private final CoordinateCreateService coordinateCreateService;

    public StoreResponse save(Long memberId, StoreCreateRequest storeCreateRequest) {
        return storeCreateService.save(memberId, storeCreateRequest);
    }

    public void delete(Long storeId) {
        storeDeleteService.delete(storeId);
    }

    public StoreResponse insertCoordinate(Long memberId, Long storeId, CoordinateRequest coordinateRequest) {
        return coordinateCreateService.insertCoordinates(memberId, storeId, coordinateRequest);
    }
}
