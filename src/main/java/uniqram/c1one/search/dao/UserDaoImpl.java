package uniqram.c1one.search.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import uniqram.c1one.search.dto.UserSearchResultDto;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
	
	private final EntityManager em;
	
	@Override
	public List<UserSearchResultDto> findUserByName(String username) {
		
		// 유저명을 정확히 검색해야함 LIKE 처리하지 않음
		// role = 'admin' 인 경우 관리자 계정으로 검색에서 제외시킴
		String sql = "SELECT user_id, username FROM `users` WHERE username = :username AND role != 'admin'";
		
		Query nativeQuery = em.createNativeQuery(sql);
		nativeQuery.setParameter("username", username);
		
		List<Object[]> results = nativeQuery.getResultList();
		if (results.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<UserSearchResultDto> dtoList = new ArrayList<>();
		for (Object[] row : results) {
			dtoList.add(UserSearchResultDto.builder()
			                               .userid(((Number) row[0]).longValue())
			                               .username((String) row[1])
			                               .build());
		}
		
		return dtoList;
	}
}
