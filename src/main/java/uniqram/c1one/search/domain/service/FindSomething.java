package uniqram.c1one.search.domain.service;


import java.util.Optional;
import uniqram.c1one.search.application.dto.UserSearchResultDto;

public interface FindSomething {

	Optional<UserSearchResultDto> findUser(String name);
	
	Optional<UserSearchResultDto> findHashtag(String hashtag);
	
}
