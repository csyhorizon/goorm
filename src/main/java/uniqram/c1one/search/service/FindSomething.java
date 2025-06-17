package uniqram.c1one.search.service;


import java.util.Optional;
import uniqram.c1one.search.dto.UserSearchResultDto;

public interface FindSomething {

	Optional<UserSearchResultDto> findUser();
	
	void findHashtag();
	
}
