package com.company;

import java.util.*;

public class Book {

    Book(String library, int id, String author, String title, Date date, String subscriber){
        this.library = library;
        this.id = id;
        this.author = author;
        this.title = title;
        this.date = date;
        this.subscriber = subscriber;
    }

    Book(String library, int id, String author, String title){
        this(library, id, author, title, null, null);
    }

    private int id;
    private String author;
    private String title;
    private Date date;
    private String subscriber;
    private String library;

    public String toString(){
        return "lib:"+library+"; id:"+id+"; author:"+author+"; title:"+title+"; date:"+date+"; sub:"+subscriber;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public String getLibrary() {
        return library;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }
}
