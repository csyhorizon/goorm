package support.fixture;

import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.entity.MemberRole;
import lombok.Getter;

@Getter
public enum MemberFixture {
    회원("test@naver.com", "testtest", "김회원", MemberRole.MEMBER);
    private String email;
    private String password;
    private String name;
    private MemberRole memberRole;

    MemberFixture(String email, String password, String name, MemberRole memberRole) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.memberRole = memberRole;
    }

    public static Member 김회원() {
        return new Member(회원.email, 회원.password, 회원.name, 회원.memberRole);
    }
}
