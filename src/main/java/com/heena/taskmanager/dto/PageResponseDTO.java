package com.heena.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponseDTO<T> {
    private List<T> items;
    private int currentPage;
    private int totalPages;
    private long totalItems;
}
