package com.colinven.blog.service;

import com.colinven.blog.dto.BlogResponse;
import com.colinven.blog.entity.Blog;
import com.colinven.blog.dto.BlogRequest;
import com.colinven.blog.exception.ResourceNotFoundException;
import com.colinven.blog.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public BlogResponse saveBlog(BlogRequest blogRequest) {
        Blog blog = new Blog(
                blogRequest.title(),
                blogRequest.content(),
                blogRequest.category(),
                blogRequest.tags()
        );
        blogRepository.save(blog);
        return new BlogResponse(blog);
    }

    public BlogResponse getBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog with id " + id + " not found."));
        return new BlogResponse(blog);
    }

    public List<BlogResponse> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        List<BlogResponse> blogResponseList = new ArrayList<>();
        for (Blog blog : blogs) {
            BlogResponse blogResponse = new BlogResponse(blog);
            blogResponseList.add(blogResponse);
        }
        return blogResponseList;
    }

    public BlogResponse editBlog(Long id, BlogRequest blogRequest) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog with id " + id + " not found."));
        blog.setTitle(blogRequest.title());
        blog.setContent(blogRequest.content());
        blog.setCategory(blogRequest.category());
        blog.setTags(blogRequest.tags());
        blog.setUpdatedAt(LocalDateTime.now());
        blogRepository.save(blog);
        return new BlogResponse(blog);
    }

    public void deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog with id " + id + " not found."));
        blogRepository.delete(blog);
    }

}
