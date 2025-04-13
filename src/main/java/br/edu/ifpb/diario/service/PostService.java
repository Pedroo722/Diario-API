package br.edu.ifpb.diario.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.edu.ifpb.diario.dto.PostRequestDTO;
import br.edu.ifpb.diario.exceptions.PostNotFoundException;
import br.edu.ifpb.diario.exceptions.UnauthorizedAccessException;
import br.edu.ifpb.diario.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.edu.ifpb.diario.model.Post;
import br.edu.ifpb.diario.repository.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post savePost(PostRequestDTO post) {
        Post newPost = new Post();
        newPost.setTitle(post.title());
        newPost.setImage(post.image());
        newPost.setBody(post.body());
        newPost.setCreatedAt(LocalDate.now());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newPost.setUser(user);

        return postRepository.save(newPost);
    }

    public Post updatePost(Long id, PostRequestDTO post) {
        Optional<Post> existingPost = getPostById(id);

        if(existingPost.isEmpty()) {
            throw new PostNotFoundException();
        }

        String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User postUser = existingPost.get().getUser();

        if(!postUser.getEmail().equals(userEmail)) {
            throw new UnauthorizedAccessException();
        }

        Post newPost = new Post();
        newPost.setTitle(post.title());
        newPost.setImage(post.image());
        newPost.setBody(post.body());

        User user = userService.findUserByEmail(userEmail);
        newPost.setUser(user);

        return postRepository.save(newPost);
    }

    public void deletePost(Long id) {
        Optional<Post> existingPost = getPostById(id);

        if(existingPost.isEmpty()) {
            throw new PostNotFoundException();
        }

        String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User postUser = existingPost.get().getUser();

        if(!postUser.getEmail().equals(userEmail)) {
            throw new UnauthorizedAccessException();
        }

        postRepository.deleteById(id);
    }

    public List<Post> findPostByUser(Long userId) {
        return postRepository.findByUserId(userId);
    }
}
