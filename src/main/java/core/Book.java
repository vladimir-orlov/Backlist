package core;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;
@Entity
@Table(name = "book")
public class Book {

    public Book(String library, int id, String author, String title, Date date, String subscriber){
        this.library = library;
        this.id = id;
        this.author = author;
        this.title = title;
        this.date = date;
        this.subscriber = subscriber;
    }

    private int id;
    private String author;
    private String title;
    private Date date;
    private String subscriber;
    private String library;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", subscriber='" + subscriber + '\'' +
                ", library='" + library + '\'' +
                '}';
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

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLibrary(String library) {
        this.library = library;
    }
}
