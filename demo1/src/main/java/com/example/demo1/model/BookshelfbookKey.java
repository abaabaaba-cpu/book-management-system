package com.example.demo1.model;

import org.springframework.stereotype.Component;

@Component
public class BookshelfbookKey {
    private Integer bookshelfid;

    private Integer bookid;

    public Integer getBookshelfid() {
        return bookshelfid;
    }

    public void setBookshelfid(Integer bookshelfid) {
        this.bookshelfid = bookshelfid;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }
}