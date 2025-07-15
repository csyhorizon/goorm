package uniqram.c1one.search.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import uniqram.c1one.search.entity.SearchHistory;

// 검색 기록에 대한 저장
// 다른 유저에게 다른 유저를 보여주기 때문에 민감한 정보 저장X
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
	
	// 검색 기록 저장
	// spring data JPA 가 자동 생성함
//	SearchHistory save(SearchHistory searchHistory);
	
	// userid 기반으로 검색 기록 불러오기
	List<SearchHistory> findAllByUserid(Long userid);
	
}
