package com.colinven.blog.service;

import com.colinven.blog.entity.Blog;
import com.colinven.blog.dto.BlogRecord;
import com.colinven.blog.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Blog saveBlog(BlogRecord blogRecord) {

        Blog blog = new Blog(
                blogRecord.title(),
                blogRecord.content(),
                blogRecord.category(),
                blogRecord.tags()
        );
        blogRepository.save(blog);
        return blog;
    }

    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog editBlog(Long id, BlogRecord blogRecord) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog != null) {
            blog.setTitle(blogRecord.title());
            blog.setContent(blogRecord.content());
            blog.setCategory(blogRecord.category());
            blog.setTags(blogRecord.tags());
            blog.setUpdatedAt(LocalDateTime.now());
            blogRepository.save(blog);
        }
        return blog;
    }

    public Blog deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog != null) {
            blogRepository.delete(blog);
        }
        return blog;
    }

}
