package br.edu.ifpb.diario.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ifpb.diario.model.Post;
import br.edu.ifpb.diario.service.PostService;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/create")
    public ResponseEntity<Post> savePost(@RequestParam String title,
                                         @RequestParam String body,
                                         @RequestParam("image") MultipartFile image) {
        Post savedPost = postService.savePost(title, body, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id,
                                           @RequestParam String title,
                                           @RequestParam String body,
                                           @RequestParam("image") MultipartFile image) {
        Post updatedPost = postService.updatePost(id, title, body, image);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/delete/{id}")
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