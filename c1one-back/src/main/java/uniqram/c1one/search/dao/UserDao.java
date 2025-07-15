package uniqram.c1one.search.dao;

import java.util.List;
import java.util.Optional;
import uniqram.c1one.search.dto.UserSearchResultDto;

public interface UserDao {
	
	List<UserSearchResultDto> findUserByName(String name);
	Optional<UserSearchResultDto> findUserByUserId(Long userid);
}
