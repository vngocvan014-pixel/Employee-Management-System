package com.ems.dto;

import java.util.List;

public class PageResponseDTO<T> {

    private List<T> data;
    private int page;
    private int size;
    private int totalElements;
    private int totalPages;

    public PageResponseDTO() {
    }

    public PageResponseDTO(
            List<T> data,
            int page,
            int size,
            int totalElements) {

        this.data = data;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;

        this.totalPages =
                (int) Math.ceil(
                        (double) totalElements / size
                );
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
