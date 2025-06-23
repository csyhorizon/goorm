package uniqram.c1one.search.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import uniqram.c1one.search.entity.SearchHistory;

@Repository
@RequiredArgsConstructor
public class SearchHistoryRepositoryImpl implements SearchHistoryRepository{
	
	private final EntityManager em;
	
	// 검색 기록 저장
	@Override
	public SearchHistory save(SearchHistory searchHistory) {
		em.persist(searchHistory);
		return searchHistory;
	}
}
