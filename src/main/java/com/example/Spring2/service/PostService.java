package com.example.Spring2.service;

import com.example.Spring2.entity.Post;
import com.example.Spring2.entity.User;
import com.example.Spring2.repository.PostRepository;
import com.example.Spring2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        postRepository.findAll().forEach(posts::add);
        return posts;
    }
    public List<Post> getpostByUserId(Long userId) {
        return postRepository.findByAuthorId(userId);
    }

    public Post getpostById(Long id) {
        return postRepository.findById(id).get();
    }


    public Post insert(Post post) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmailIgnoreCase(email).get();
        post.setAuthor(user);
        return postRepository.save(post);
    }

    public Post updatepost(Long id, Post post) {
        Post postFromDb = postRepository.findById(id).get();
        System.out.println(postFromDb.toString());
        postFromDb.setDescription(post.getDescription());
        postFromDb.setTitle(post.getTitle());
        post = postRepository.save(postFromDb);
        return post;
    }

    public void deletepost(Long postId) {
        postRepository.deleteById(postId);
    }
}
