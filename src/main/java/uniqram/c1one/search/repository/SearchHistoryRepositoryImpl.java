// 일단 주석처리해서 보관 나중에 사용할지말지 결정

//package uniqram.c1one.search.repository;
//
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//import uniqram.c1one.search.entity.SearchHistory;
//
//import java.util.List;
//
////@Repository
//@RequiredArgsConstructor
//public class SearchHistoryRepositoryImpl implements SearchHistoryRepository{
//
//	private final EntityManager em;
//
//	// 검색 기록 저장
//	@Override
//	public SearchHistory save(SearchHistory searchHistory) {
//		em.persist(searchHistory);
//		return searchHistory;
//	}
//
//	// 어떤 유저의 모든 검색기록 조회
//	@Override
//	public List<SearchHistory> findAll(Long userid) {
//		return List.of();
//	}
//}
