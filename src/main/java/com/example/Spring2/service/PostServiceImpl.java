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
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


//  Get All posts from db
    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        postRepository.findAll().forEach(posts::add);
        return posts;
    }
    // Get Specified User's Posts
    public List<Post> getpostByUserId(Long userId) {
        return postRepository.findByAuthorId(userId);
    }

    // Get Single Post with specified id
    public Post getpostById(Long id) {
        return postRepository.findById(id).get();
    }

    // Save Post in DB
    public Post insert(Post post) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmailIgnoreCase(email).get();
        post.setAuthor(user);
        return postRepository.save(post);
    }

    // Update post with specified Id 
    public Post updatepost(Long id, Post post) {
        Post postFromDb = postRepository.findById(id).get();
        postFromDb.setDescription(post.getDescription());
        postFromDb.setTitle(post.getTitle());
        post = postRepository.save(postFromDb);
        return post;
    }

    // Delete post from Db
    public void deletepost(Long postId) throws Exception {
        // Check if post exists
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()){
            throw new Exception("Invalid Post ID");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmailIgnoreCase(email).get();

        // Check if post belongs to the user 
        if(post.get().getAuthor().getId() != user.getId()){
            throw new Exception("Unauthorized action");
        }
        postRepository.deleteById(postId);
    }
}
