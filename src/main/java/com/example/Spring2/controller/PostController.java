package com.example.Spring2.controller;

import com.example.Spring2.entity.Post;
import com.example.Spring2.service.PostService;
import com.example.Spring2.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createPost(@Valid @RequestBody Post post) throws Exception {
        return new ResponseEntity<>(postService.insert(post), HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<?> getPosts() throws Exception {
        return new ResponseEntity<>(postService.getPosts(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPostById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(postService.getpostById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPostByUserId(@PathVariable Long userId) throws Exception {
        return new ResponseEntity<>(postService.getpostByUserId(userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePost(@PathVariable Long id,@Valid @RequestBody Post post) throws Exception {
        return new ResponseEntity<>(postService.updatepost(id, post), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        try{
            postService.deletepost(id);
            return new ResponseEntity<>("Post deleted successfully.", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
