package uniqram.c1one.search.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import uniqram.c1one.global.BaseEntity;

// 검색 결과를 DB 에 저장
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchHistory extends BaseEntity {
	
	// 저장용 인조키(Artificial Key)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Users table 의 id 와 같음(누가 검색했는지)
	@Column(name = "userid")
	private Long userid;
	
	// 사용자가 검색할 내용
	private String searchKeyword;
	
	// Enum 만들기 시간 부족 추후 구현...
	//	private SearchType searchType;
	
	@Builder
	public SearchHistory(Long userid, String searchKeyword) {
		this.userid = userid;
		this.searchKeyword = searchKeyword;
	}
}
