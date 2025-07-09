package uniqram.c1one.search.dao;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HashtagDaoImpl implements HashtagDao{
	
	private final EntityManager em;
	
}
