package com.colinven.blog.dto;

import java.time.LocalDateTime;

public record APIError(LocalDateTime timestamp, int statusCode, String errorMessage, String message, String path) {

}
