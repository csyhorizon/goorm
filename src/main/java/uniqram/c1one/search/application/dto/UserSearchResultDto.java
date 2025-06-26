package uniqram.c1one.search.application.dto;


import lombok.Getter;

// 유저 검색 결과 dto
@Getter
public class UserSearchResultDto {
	
	private String username;
	
	protected UserSearchResultDto(){}
	
	public UserSearchResultDto(String username) {
		this.username = username;
	}
}
