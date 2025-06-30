package uniqram.c1one.search.service;


import java.util.List;
import uniqram.c1one.search.dto.UserSearchResultDto;

public interface FindUser {
	
	List<UserSearchResultDto> findUser(String username);
	
	// 미구현
//	List<UserSearchResultDto> findHashtag(String hashtag);
	
}
