package uniqram.c1one.search.dto;


import lombok.Builder;
import lombok.Getter;

// by 유저 검색 결과 DTO
@Getter
public class UserSearchResultDto {
	
	private Long userid;
	
	private String username;
	
	protected UserSearchResultDto(){}
	
	@Builder
	public UserSearchResultDto(Long userid, String username) {
		this.userid = userid;
		this.username = username;
	}
}
