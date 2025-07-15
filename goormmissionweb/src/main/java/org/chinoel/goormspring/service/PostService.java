package org.chinoel.goormspring.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.chinoel.goormspring.dto.request.PostAddDto;
import org.chinoel.goormspring.entity.Post;
import org.chinoel.goormspring.entity.User;
import org.chinoel.goormspring.exception.PostNotFoundException;
import org.chinoel.goormspring.repository.PostRepository;
import org.chinoel.goormspring.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post getPost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Long addPost(PostAddDto postData, String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("로그인되지 않은 상태입니다."));

        Post post = Post.addPost(postData, user);
        postRepository.save(post);

        return post.getId();
    }
}
