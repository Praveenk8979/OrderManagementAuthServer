package com.ordermanagement.OrderManagementAndAuthServer.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResponseDto<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public PageResponseDto(List<T> content, int page, int size,
                           long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
