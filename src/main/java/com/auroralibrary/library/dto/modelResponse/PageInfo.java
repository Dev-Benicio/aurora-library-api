package com.auroralibrary.library.dto.modelResponse;

public class PageInfo {
    private int size;
    private Long totalElements;
    private int totalPages;
    private int numberPage;

    public PageInfo(int size, Long totalElements, int totalPages, int number) {
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.numberPage = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(int number) {
        this.numberPage = number;
    }
}
