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
        Blog blog = blogService.saveBlog(blogRecord);
        BlogResponse savedBlog = new BlogResponse(
                blog.getId(),
                blog.getTitle(),
                blog.getContent(),
                blog.getCategory(),
                blog.getTags()
        );

        return ResponseEntity.created(URI.create("/api/v1/blogs/" + blog.getId())).body(savedBlog);
    }

    @GetMapping("/blogs/{id}")
    public ResponseEntity<BlogResponse> getBlog(@PathVariable("id") Long id) {
        Blog blog = blogService.getBlog(id);
        if (blog == null) {
            return ResponseEntity.notFound().build();
        }
        BlogResponse blogResponse = new BlogResponse(blog);
        return ResponseEntity.ok(blogResponse);
    }

    @GetMapping("/blogs")
    public ResponseEntity<List<BlogResponse>> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
        List<BlogResponse> blogResponses = new ArrayList<>();
        for (Blog blog : blogs) {
            blogResponses.add(new BlogResponse(blog));
        }
        return ResponseEntity.ok(blogResponses);
    }

    @PutMapping("/blogs/{id}")
    public ResponseEntity<BlogResponse> editBlog(@PathVariable("id") Long id, @RequestBody BlogRecord blogRecord) {
        Blog blog = blogService.editBlog(id, blogRecord);
        if (blog == null) {
            return ResponseEntity.notFound().build();
        }
        BlogResponse updatedBlog = new BlogResponse(blog);
        return ResponseEntity.ok(updatedBlog);
    }

    @DeleteMapping("/blogs/{id}")
    public  ResponseEntity<Void> deleteBlog(@PathVariable("id") Long id) {
        Blog blog = blogService.deleteBlog(id);
        if (blog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
