package com.thebongcoder.blog.blogapp.dto;

import lombok.Data;

@Data
public class PostResponse {

    private Object content;

    private int pageNo;

    private int pageSize;

    private long totalElements; // total number of data in db

    private int totalPages;

    private boolean last;
}
