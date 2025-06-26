package uniqram.c1one.search.domain.repository;

import java.util.List;
import uniqram.c1one.search.domain.entity.SearchHistory;

// 검색 기록에 대한 저장
// 다른 유저에게 다른 유저를 보여주기 때문에 민감한 정보 저장X
public interface SearchHistoryRepository {
	
	// 검색 기록 저장
	SearchHistory saveSearchHistory(SearchHistory searchHistory);
	
	// 검색 기록 삭제
	SearchHistory deleteSearchHistory(Long id);
	
	// userid 기반으로 검색 기록 불러오기
	List<SearchHistory> findAll(Long userid);
}
