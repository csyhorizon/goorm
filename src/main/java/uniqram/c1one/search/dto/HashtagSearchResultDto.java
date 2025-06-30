package uniqram.c1one.search.dto;

import lombok.Builder;
import lombok.Getter;

// by 해시태그 검색 결과 DTO
@Getter
public class HashtagSearchResultDto {
	
	private String Hashtag;
	
	protected HashtagSearchResultDto(){}
	
	@Builder
	public HashtagSearchResultDto(String Hashtag) {
		this.Hashtag = Hashtag;
	}
}
