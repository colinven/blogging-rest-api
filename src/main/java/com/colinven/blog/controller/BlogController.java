package com.colinven.blog.controller;

import com.colinven.blog.entity.Blog;
import com.colinven.blog.dto.BlogRecord;
import com.colinven.blog.service.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/blogs")
    public ResponseEntity<Void> createBlog(@RequestBody BlogRecord blogRecord) {
        Blog newBlog = blogService.saveBlog(blogRecord);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newBlog.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/blogs/{id}")
    public ResponseEntity<Blog> getBlog(@PathVariable("id") Long id) {
        Blog blog = blogService.getBlog(id);
        if (blog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(blog);
    }

    @GetMapping("/blogs")
    public ResponseEntity<List<Blog>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    @PutMapping("/blogs/{id}")
    public ResponseEntity<Blog> editBlog(@PathVariable("id") Long id, @RequestBody BlogRecord blogRecord) {
        Blog blog = blogService.editBlog(id, blogRecord);
        if (blog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(blog);
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
