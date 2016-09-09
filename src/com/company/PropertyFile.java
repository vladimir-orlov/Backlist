package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertyFile implements BaseBookWorker {
    @Override
    public List<Book> returnAllBook(File propertyFile) {
        List<Book> book = new ArrayList<>();
        Properties property = new Properties();

        try(FileInputStream fis = new FileInputStream(propertyFile)) {
            property.load(fis);
            int id = Integer.parseInt(property.getProperty("Index"));
            String author = property.getProperty("Author");
            String title = property.getProperty("Name");
            String subscriber = property.getProperty("Issued");
            String date = property.getProperty("Issuedto");
            book.add(new Book(propertyFile.getParent(), propertyFile.getName(), id, author, title, date, subscriber));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public void writeChanges(String filename, List<Book> books) {
        if(books.size() > 1){
            System.out.println("Too many elements for this object.");
        }
        StringBuilder builder = new StringBuilder();
        Book book = books.get(0);

        builder.append("Index="+book.getId());
        builder.append("Author="+book.getAuthor());
        builder.append("Name="+book.getTitle());
        builder.append("Issued="+book.getDate());
        builder.append("Issuedto="+book.getSubscriber());

        TextFile.write(filename, builder.toString());
    }
}
