package uniqram.c1one.search.service;


import java.util.List;
import java.util.Optional;
import uniqram.c1one.search.dto.UserSearchResultDto;

public interface FindUser {
	
	List<UserSearchResultDto> findUser(String username);
	Optional<UserSearchResultDto> findUserByUserId(Long userId);
	
	// 미구현
//	List<UserSearchResultDto> findHashtag(String hashtag);
	
}
