package br.edu.ifpb.diario.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.edu.ifpb.diario.exceptions.PostNotFoundException;
import br.edu.ifpb.diario.exceptions.UnauthorizedAccessException;
import br.edu.ifpb.diario.model.User;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.edu.ifpb.diario.model.Post;
import br.edu.ifpb.diario.repository.PostRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinary;

    public Page<Post> getAllPosts(int page, int size) {
        return postRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post savePost(String title, String body, MultipartFile image) {
        String imagePath = saveImage(image);

        Post newPost = new Post();
        newPost.setTitle(title);
        newPost.setImage(imagePath);
        newPost.setBody(body);
        newPost.setCreatedAt(LocalDate.now());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newPost.setUser(user);

        return postRepository.save(newPost);
    }

    public Post updatePost(Long id, String title, String body, MultipartFile image) {
        Optional<Post> existingPost = getPostById(id);

        if(existingPost.isEmpty()) {
            throw new PostNotFoundException();
        }

        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = loggedUser.getEmail();
        User postUser = existingPost.get().getUser();

        if(!postUser.getEmail().equals(userEmail)) {
            throw new UnauthorizedAccessException();
        }

        String imagePath = saveImage(image);

        Post newPost = new Post();
        newPost.setId(existingPost.get().getId());
        newPost.setTitle(title);
        newPost.setImage(imagePath);
        newPost.setBody(body);
        newPost.setCreatedAt(existingPost.get().getCreatedAt());

        User user = userService.findUserByEmail(userEmail);
        newPost.setUser(user);

        return postRepository.save(newPost);
    }

    private String saveImage(MultipartFile image) {
        try {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload da imagem para o Cloudinary", e);
        }
    }

    public void deletePost(Long id) {
        Optional<Post> existingPost = getPostById(id);

        if(existingPost.isEmpty()) {
            throw new PostNotFoundException();
        }

        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = loggedUser.getEmail();
        User postUser = existingPost.get().getUser();

        if(!postUser.getEmail().equals(userEmail)) {
            throw new UnauthorizedAccessException();
        }

        postRepository.deleteById(id);
    }

    public Page<Post> findPostByUser(Long userId, int page, int size) {
        return postRepository.findByUserId(userId, PageRequest.of(page, size));
    }
}
