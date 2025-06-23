package uniqram.c1one.search.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import uniqram.c1one.global.BaseEntity;

@Entity
public class SearchHistory extends BaseEntity {
	
	// 저장용 인조키(Artificial Key)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Users table 의 id 와 같음(누가 검색했는지)
	@Column(name = "userid")
	private Long userid;
	
	// SearchResult 검색한 내용
	private String result;
	
	// Enum 만들기
	
}
