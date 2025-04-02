package org.chinoel.goormspring.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.chinoel.goormspring.dto.request.PostAddDto;
import org.chinoel.goormspring.entity.Comments;
import org.chinoel.goormspring.entity.Post;
import org.chinoel.goormspring.service.CommentService;
import org.chinoel.goormspring.service.PostService;
import org.chinoel.goormspring.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    @GetMapping(value = "/post/postwrite")
    public String postWritePage() {
        return "post/write";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable("id") Long id,
                          Model model,
                          @AuthenticationPrincipal UserDetails userDetails) {
        Post post = postService.getPost(id);

        if(post.getAccessLevel().equals("PRIVATE") &&
        !post.getUser().getUsername().equals(userDetails.getUsername())) {
            model.addAttribute("secret", true);
            model.addAttribute("posts", postService.getAllPosts());
            return "post/list";
        }

        List<Comments> comments = commentService.getCommentsByPostId(id);

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);

        return "post/detail";
    }

    @GetMapping("/post")
    public String getPostList(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "post/list";
    }

    @PostMapping("/post/{id}/comment")
    public String addComment(@PathVariable("id") Long id,
                             @RequestParam("content") String content,
                             @AuthenticationPrincipal UserDetails userDetails) {

        commentService.addComment(postService.getPost(id), userService.findUserByID(userDetails.getUsername()), content);

        return "redirect:/post/" + id;
    }

    @PostMapping("/post/postwrite")
    public String postWrite(@Valid PostAddDto postAddDto, BindingResult result, @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            return "post/write";
        }

        Long postId = postService.addPost(postAddDto, userDetails.getUsername());

        return "redirect:/post/" + postId;
    }
}
