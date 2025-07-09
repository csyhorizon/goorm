package uniqram.c1one.search.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniqram.c1one.search.dao.UserDao;
import uniqram.c1one.search.dao.UserDaoImpl;
import uniqram.c1one.search.dto.UserSearchResultDto;


@Service
@RequiredArgsConstructor
public class FindUserImpl implements FindUser {
	
	private final UserDao userDao;
	
	@Override
	public List<UserSearchResultDto> findUser(String username) {
		return userDao.findUserByName(username);
	}
}
