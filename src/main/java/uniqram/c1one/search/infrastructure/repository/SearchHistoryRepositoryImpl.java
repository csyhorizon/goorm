package uniqram.c1one.search.infrastructure.repository;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import uniqram.c1one.search.domain.entity.SearchHistory;
import uniqram.c1one.search.domain.repository.SearchHistoryRepository;

@Repository
@RequiredArgsConstructor
public class SearchHistoryRepositoryImpl implements SearchHistoryRepository {
	
	private final EntityManager em;
	
	// 검색 기록 저장
	@Override
	public SearchHistory saveSearchHistory(SearchHistory searchHistory) {
		em.persist(searchHistory);
		return searchHistory;
	}
	
	// 검색 기록 삭제
	@Override
	public SearchHistory deleteSearchHistory(Long id) {
		return null;
	}
	
	
	@Override
	public List<SearchHistory> findAll(Long userid) {
	
		List<SearchHistory> result = new ArrayList<>();
		
		return result;
	}
}
