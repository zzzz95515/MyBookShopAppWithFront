package com.example.MyBookShopApp.data;

public class SearchWordDto {
    private String example;

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public SearchWordDto() {
    }

    public SearchWordDto(String example) {
        this.example = example;
    }
}