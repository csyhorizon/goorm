package uniqram.c1one.search.application.dto;

import lombok.Getter;


@Getter
public class HashtagSearchResultDto {
	
	private String username;
	
	private String email;
	
	protected HashtagSearchResultDto(){}
	
	public HashtagSearchResultDto(String username, String email) {
		this.username = username;
		this.email = email;
	}
}
