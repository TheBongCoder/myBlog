package com.thebongcoder.blog.blogapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class JwtAuthResponseDTO {

    private String accessToken;

    private String tokenType = "Bearer";

}
