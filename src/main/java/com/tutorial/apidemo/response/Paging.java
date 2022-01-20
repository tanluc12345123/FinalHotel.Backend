package com.tutorial.apidemo.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class Paging<T> {
    private final long totalPages;

    private final long totalElements;

    private final long pageSize;

    private final long pageNumber;

    private final List<T> content;

    public Paging(Page<T> page) {
        page = (page == null) ? Page.empty() : page;
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.pageSize = page.getSize();
        this.pageNumber = page.getNumber();
        this.content = page.getContent();
    }

}
