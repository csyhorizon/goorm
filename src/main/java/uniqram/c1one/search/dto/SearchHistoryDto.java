package uniqram.c1one.search.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchHistoryDto {
	
	private Long userid;
	private String searchKeyword;
	
	public SearchHistoryDto() {}
	
	@Builder
	public SearchHistoryDto(Long userid, String searchKeyword) {
		this.userid = userid;
		this.searchKeyword = searchKeyword;
	}
}
