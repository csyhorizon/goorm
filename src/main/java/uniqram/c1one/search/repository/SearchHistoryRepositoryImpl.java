package uniqram.c1one.search.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import uniqram.c1one.search.entity.SearchHistory;

import java.util.List;

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

	@Override
	public List<SearchHistory> findAll(Long userid) {
		return List.of();
	}
}
