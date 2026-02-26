package com.colinven.blog.service;

import com.colinven.blog.dto.BlogResponse;
import com.colinven.blog.entity.Blog;
import com.colinven.blog.dto.BlogRecord;
import com.colinven.blog.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            return null;
        }
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
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            return null;
        }
        blog.setTitle(blogRecord.title());
        blog.setContent(blogRecord.content());
        blog.setCategory(blogRecord.category());
        blog.setTags(blogRecord.tags());
        blog.setUpdatedAt(LocalDateTime.now());
        blogRepository.save(blog);
        return new BlogResponse(blog);
    }

    public BlogResponse deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            return null;
        }
        blogRepository.delete(blog);
        return new BlogResponse(blog);
    }

}
