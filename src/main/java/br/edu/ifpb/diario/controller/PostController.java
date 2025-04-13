package br.edu.ifpb.diario.controller;

import br.edu.ifpb.diario.dto.PostRequestDTO;
import br.edu.ifpb.diario.model.Post;
import br.edu.ifpb.diario.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/list")
    public ResponseEntity<List<Post>> getAllPosts(
            @RequestParam(value = "userId", required = false) Long userId) {

        List<Post> posts;

        if (userId != null) {
            posts = postService.findPostByUser(userId);
        } else {
            posts = postService.getAllPosts();
        }

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);

        if (post.isPresent()) {
            return ResponseEntity.ok(post.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<Post> savePost(@RequestBody PostRequestDTO post) {
        Post savedPost = postService.savePost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostRequestDTO post) {
        Post updatedPost = postService.updatePost(id, post);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        Optional<Post> existingPost = postService.getPostById(id);
        if (existingPost.isPresent()) {
            postService.deletePost(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}