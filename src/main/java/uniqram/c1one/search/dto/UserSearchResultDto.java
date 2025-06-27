package uniqram.c1one.search.dto;


import lombok.Getter;

// 유저 검색 결과 dto
@Getter
public class UserSearchResultDto {
	
	private String username;
	
	private String email;
	
	protected UserSearchResultDto(){}
	
	public UserSearchResultDto(String username, String email) {
		this.username = username;
		this.email = email;
	}
}
