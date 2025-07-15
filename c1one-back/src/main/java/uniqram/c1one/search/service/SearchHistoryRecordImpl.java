package uniqram.c1one.search.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniqram.c1one.search.entity.SearchHistory;
import uniqram.c1one.search.repository.SearchHistoryRepository;

@Service
@RequiredArgsConstructor
public class SearchHistoryRecordImpl implements SearchHistoryRecord {
	
	private final SearchHistoryRepository searchHistoryRepository;
	
	@Override
	public SearchHistory searchHistoryRecord(Long userid, String searchKeyword) {
		
		SearchHistory searchHistory = SearchHistory.builder()
		                                           .userid(userid)
		                                           .searchKeyword(searchKeyword)
		                                           .build();
		
		
		searchHistoryRepository.save(searchHistory);
		return searchHistory;
	}
	

}
