package com.colinven.blog.dto;

import java.util.List;

public record BlogRecord(String title, String content, String category, List<String> tags) {
}
