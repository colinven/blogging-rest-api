package com.colinven.blog.controller;

import com.colinven.blog.dto.BlogResponse;
import com.colinven.blog.entity.Blog;
import com.colinven.blog.dto.BlogRecord;
import com.colinven.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/blogs")
    public ResponseEntity<BlogResponse> createBlog(@RequestBody BlogRecord blogRecord) {
        BlogResponse blogResponse = blogService.saveBlog(blogRecord);
        return ResponseEntity.created(URI.create("/api/v1/blogs/" + blogResponse.id())).body(blogResponse);
    }

    @GetMapping("/blogs/{id}")
    public ResponseEntity<BlogResponse> getBlog(@PathVariable("id") Long id) {
        BlogResponse blogResponse = blogService.getBlog(id);
        if (blogResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(blogResponse);
    }

    @GetMapping("/blogs")
    public ResponseEntity<List<BlogResponse>> getAllBlogs() {
        List<BlogResponse> blogResponses = blogService.getAllBlogs();
        return ResponseEntity.ok(blogResponses);
    }

    @PutMapping("/blogs/{id}")
    public ResponseEntity<BlogResponse> editBlog(@PathVariable("id") Long id, @RequestBody BlogRecord blogRecord) {
        BlogResponse blogResponse = blogService.editBlog(id, blogRecord);
        if (blogResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(blogResponse);
    }

    @DeleteMapping("/blogs/{id}")
    public  ResponseEntity<Void> deleteBlog(@PathVariable("id") Long id) {
        BlogResponse blogResponse = blogService.deleteBlog(id);
        if (blogResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
