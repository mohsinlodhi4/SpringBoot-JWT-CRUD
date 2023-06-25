package com.example.Spring2.service;

import com.example.Spring2.entity.Post;
import com.example.Spring2.entity.User;
import com.example.Spring2.repository.PostRepository;
import com.example.Spring2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public interface PostService {


    public List<Post> getPosts();
    public List<Post> getpostByUserId(Long userId);

    public Post getpostById(Long id);


    public Post insert(Post post);

    public Post updatepost(Long id, Post post);

    public void deletepost(Long postId) throws Exception;
}
