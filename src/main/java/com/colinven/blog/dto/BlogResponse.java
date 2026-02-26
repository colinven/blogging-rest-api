package com.colinven.blog.dto;

import java.util.List;

public record BlogResponse(long id, String title, String content, String category, List<String> tags) {
}
