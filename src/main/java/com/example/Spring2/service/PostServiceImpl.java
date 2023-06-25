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

    public void deletepost(Long postId) throws Exception {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()){
            throw new Exception("Invalid Post ID");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmailIgnoreCase(email).get();

        if(post.get().getAuthor().getId() != user.getId()){
            throw new Exception("Unauthorized action");
        }
        postRepository.deleteById(postId);
    }
}
