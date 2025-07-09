package uniqram.c1one.search.dao;

import java.util.List;
import uniqram.c1one.search.dto.UserSearchResultDto;

public interface UserDao {
	
	List<UserSearchResultDto> findUserByName(String name);
}
