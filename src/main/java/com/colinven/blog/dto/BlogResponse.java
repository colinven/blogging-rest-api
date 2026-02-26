package com.colinven.blog.dto;

import com.colinven.blog.entity.Blog;

import java.util.List;

public record BlogResponse(long id, String title, String content, String category, List<String> tags) {

    public BlogResponse(Blog blog) {
        this(blog.getId(), blog.getTitle(), blog.getContent(), blog.getCategory(), blog.getTags());
    }
}
