package com.example.backend.domain.comment.service;

import com.example.backend.domain.comment.entity.Comment;
import com.example.backend.domain.comment.repository.CommentRepository;
import com.example.backend.domain.comment.service.command.CommentCommandService;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.post.entity.Post;
import com.example.backend.domain.post.repository.PostRepository;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import com.example.backend.support.annotation.ServiceTest;
import com.example.backend.support.fixture.CommentFixture;
import com.example.backend.support.fixture.MemberFixture;
import com.example.backend.support.fixture.PostFixture;
import com.example.backend.support.fixture.StoreFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ServiceTest
public class CommentQueryServiceTest {

    @Autowired
    CommentCommandService commentCommandService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    void 댓글을_생성할_수_있다(){
        Member member = MemberFixture.김회원();
        memberRepository.save(member);
        Store store = StoreFixture.과일가게(member);
        storeRepository.save(store);
        Post post = PostFixture.게시글(member, store);
        postRepository.save(post);
        Comment comment = CommentFixture.댓글(member, post);
        commentRepository.save(comment);

        assertThat(commentRepository.findAll()).hasSize(1);
    }

    @Test
    void 대댓글을_생성할_수_있다(){
        Member member = MemberFixture.김회원();
        memberRepository.save(member);
        Member member2 = MemberFixture.김회투();
        memberRepository.save(member2);
        Store store = StoreFixture.과일가게(member);
        storeRepository.save(store);
        Post post = PostFixture.게시글(member, store);
        postRepository.save(post);
        Comment parent = CommentFixture.댓글(member, post);
        commentRepository.save(parent);
        Comment child = CommentFixture.대댓글(member2, post, parent);
        commentRepository.save(child);

        assertThat(child.getParentComment().getId()).isEqualTo(parent.getId());
        assertThat(commentRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    void 댓글_작성자가_아닐_경우_댓글을_삭제할_수_없다(){
        Member member = MemberFixture.김회원();
        memberRepository.save(member);
        Member member2 = MemberFixture.김회투();
        memberRepository.save(member2);
        Store store = StoreFixture.과일가게(member);
        storeRepository.save(store);
        Post post = PostFixture.게시글(member, store);
        postRepository.save(post);
        Comment comment = CommentFixture.댓글(member, post);
        commentRepository.save(comment);

        assertThatThrownBy(
                () -> commentCommandService.deleteComment(comment.getId(), member2.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 댓글을_삭제할_수_있다(){
        Member member = MemberFixture.김회원();
        memberRepository.save(member);
        Store store = StoreFixture.과일가게(member);
        storeRepository.save(store);
        Post post = PostFixture.게시글(member, store);
        postRepository.save(post);
        Comment comment = CommentFixture.댓글(member, post);
        commentRepository.save(comment);

        commentCommandService.deleteComment(comment.getId(), member.getId());
        assertThat(commentRepository.findAll()).hasSize(0);
    }
}
