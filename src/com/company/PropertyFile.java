package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class PropertyFile implements BaseBookWorker {
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    public List<Book> returnAllBook(File propertyFile) {
        List<Book> book = new ArrayList<>();
        Properties property = new Properties();

        try(FileInputStream fis = new FileInputStream(propertyFile)) {
            property.load(fis);
            int id = Integer.parseInt(property.getProperty("Index"));
            String author = property.getProperty("Author");
            String title = property.getProperty("Name");
            Date date = formatter.parse(property.getProperty("Issued"));
            String subscriber = property.getProperty("Issuedto");
            book.add(new Book(propertyFile.getParent(), id, author, title, date, subscriber));
        } catch (IOException e) {
            System.out.println("Problem with reading .property file:");
            e.printStackTrace();
        } catch (ParseException e) {
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

        builder.append("Index="+book.getId()+"\n");
        builder.append("Author="+book.getAuthor()+"\n");
        builder.append("Name="+book.getTitle()+"\n");
        builder.append("Issued="+book.getDate() == null ? "" : book.getDate()+"\n");
        builder.append("Issuedto="+book.getSubscriber() == null ? "" : formatter.format(book.getSubscriber()));

        try(PrintWriter out = new PrintWriter(new File(filename).getAbsoluteFile())){
            out.print(builder.toString());
        } catch (IOException e) {
            System.out.println("Problem with writing to .property file:");
            e.printStackTrace();
        }
    }
}
