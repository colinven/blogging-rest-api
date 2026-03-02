package com.colinven.blog.service;

import com.colinven.blog.dto.BlogResponse;
import com.colinven.blog.entity.Blog;
import com.colinven.blog.dto.BlogRecord;
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

    public BlogResponse saveBlog(BlogRecord blogRecord) {
        Blog blog = new Blog(
                blogRecord.title(),
                blogRecord.content(),
                blogRecord.category(),
                blogRecord.tags()
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

    public BlogResponse editBlog(Long id, BlogRecord blogRecord) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog with id " + id + " not found."));
        blog.setTitle(blogRecord.title());
        blog.setContent(blogRecord.content());
        blog.setCategory(blogRecord.category());
        blog.setTags(blogRecord.tags());
        blog.setUpdatedAt(LocalDateTime.now());
        blogRepository.save(blog);
        return new BlogResponse(blog);
    }

    public void deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog with id " + id + " not found."));
        blogRepository.delete(blog);
    }

}
