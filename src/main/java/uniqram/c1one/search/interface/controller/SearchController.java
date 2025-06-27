package uniqram.c1one.search.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniqram.c1one.search.application.dto.HashtagSearchResultDto;
import uniqram.c1one.search.application.dto.SearchHistoryDto;
import uniqram.c1one.search.application.dto.UserSearchResultDto;


@RestController
@RequestMapping("/api/search")
public class SearchController {
	
	@GetMapping()
	public UserSearchResultDto searchByUsername() {
		return "search";
	}
	
	@GetMapping()
	public HashtagSearchResultDto searchByHashtag() {
		return "search";
	}
	
	@GetMapping
	public SearchHistoryDto searchHistoryList() {
	
	}

}
