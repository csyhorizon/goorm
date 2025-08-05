package com.example.backend.domain.store.repository;

import com.example.backend.domain.admin.dto.MonthlyStoreVisitStats;
import com.example.backend.domain.admin.dto.StoreEventVisitStats;
import com.example.backend.domain.event.entity.QEvent;
import com.example.backend.domain.store.entity.QStore;
import com.example.backend.domain.store.entity.QStoreDailyVisitor;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StoreDailyVisitorRepositoryImpl implements StoreDailyVisitorCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public long countJoinedStores() {
        QStore store = QStore.store;
        Long count = queryFactory
                .select(store.count())
                .from(store)
                .fetchOne();
        return count != null ? count : 0L;
    }

    @Override
    public List<StoreEventVisitStats> getStoreEventVisitStats() {
        QEvent event = QEvent.event;
        QStoreDailyVisitor visitor = QStoreDailyVisitor.storeDailyVisitor;

        return queryFactory
                .select(Projections.constructor(StoreEventVisitStats.class,
                        event.store.id,
                        event.store.name,
                        event.id,
                        event.eventInfo.title,
                        event.eventDuration.startTime,
                        event.eventDuration.endTime,
                        visitor.paymentCount.sum()
                ))
                .from(event)
                .join(visitor).on(
                        visitor.store.id.eq(event.store.id)
                                .and(visitor.visitDate.between(event.eventDuration.startTime,
                                        event.eventDuration.endTime))
                )
                .groupBy(event.id)
                .fetch();
    }

    @Override
    public List<MonthlyStoreVisitStats> getMonthlyVisitStats() {
        QStoreDailyVisitor visitor = QStoreDailyVisitor.storeDailyVisitor;
        QStore store = QStore.store;

        return queryFactory
                .select(Projections.constructor(MonthlyStoreVisitStats.class,
                        visitor.store.id,
                        visitor.store.name,
                        visitor.visitDate.year(),
                        visitor.visitDate.month(),
                        visitor.paymentCount.sum()
                ))
                .from(visitor)
                .join(visitor.store, store)
                .groupBy(
                        visitor.store.id,
                        visitor.store.name,
                        visitor.visitDate.year(),
                        visitor.visitDate.month())
                .fetch();
    }

//    @Override
//    public List<MonthlyStoreVisitStats> getMonthlyVisitStats(Long storeId) {
//        QStoreDailyVisitor visitor = QStoreDailyVisitor.storeDailyVisitor;
//        QStore store = QStore.store;
//
//        return queryFactory
//                .select(Projections.constructor(MonthlyStoreVisitStats.class,
//                        visitor.store.id,
//                        visitor.store.name,
//                        visitor.visitDate.year(),
//                        visitor.visitDate.month(),
//                        visitor.paymentCount.sum()
//                ))
//                .from(visitor)
//                .join(visitor.store, store)
//                .where(visitor.store.id.eq(storeId))
//                .groupBy(
//                        visitor.store.id,
//                        visitor.store.name,
//                        visitor.visitDate.year(),
//                        visitor.visitDate.month())
//                .fetch();
//    }
}
