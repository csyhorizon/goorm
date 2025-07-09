package uniqram.c1one.search.service;

import uniqram.c1one.search.entity.SearchHistory;

public interface SearchHistoryRecord {
	
	SearchHistory searchHistoryRecord(Long userid, String searchKeyword);

}
