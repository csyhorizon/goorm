package uniqram.c1one.search.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import uniqram.c1one.search.dto.UserSearchResultDto;

@Service
public class FindSomethingImpl implements FindSomething{
	
	@Override
	public Optional<UserSearchResultDto> findUser(String name) {
		return Optional.empty();
	}
	
	@Override
	public Optional<UserSearchResultDto> findHashtag(String hashtag) {
		return Optional.empty();
	}
}
