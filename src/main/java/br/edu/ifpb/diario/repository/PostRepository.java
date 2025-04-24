package br.edu.ifpb.diario.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.diario.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    Page<Post> findAll(Pageable pageable);
    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    Page<Post> findByUserId(Long userId, Pageable pageable);
}
