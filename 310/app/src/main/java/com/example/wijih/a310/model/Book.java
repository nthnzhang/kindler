package com.example.wijih.a310.model;

import java.util.List;

public class Book {
    private String title;
    private String description;
    private String ownerID;
    private boolean forSale;
    private List<String> tags;
    private String bookId;

    public Book(String title, String description, String ownerID, boolean forSale, List<String> tags) {
        this.title = title;
        this.description = description;
        this.ownerID = ownerID;
        this.forSale = forSale;
        this.tags = tags;
    }

    public void setBookId(String id) {
        this.bookId = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBookId() {
        return bookId;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public boolean isForSale() {
        return forSale;
    }

    public List<String> getTags() {
        return tags;
    }

}
