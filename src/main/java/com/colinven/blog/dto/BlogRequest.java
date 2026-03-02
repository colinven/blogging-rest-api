package com.colinven.blog.dto;

import java.util.List;

public record BlogRequest(String title, String content, String category, List<String> tags) {
}
