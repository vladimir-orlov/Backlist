package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class PropertyFile implements BaseBookWorker {
    @Override
    public List<Book> returnAllBook(File propertyFile) {
        List<Book> book = new ArrayList<>();
        Properties property = new Properties();

        try(FileInputStream fis = new FileInputStream(propertyFile)) {
            property.load(fis);
            int id = Integer.parseInt(property.getProperty(Constants.PROPERTY_INDEX));
            String author = property.getProperty(Constants.PROPERTY_AUTHOR);
            String title = property.getProperty(Constants.PROPERTY_TITLE);
            Date date = Librarians.convertStringToDate(property.getProperty(Constants.PROPERTY_DATE));
            String subscriber = property.getProperty(Constants.PROPERTY_SUBSCRIBER);
            book.add(new Book(propertyFile.getParent(), id, author, title, date, subscriber));
        } catch (IOException e) {
            System.out.println("Problem with reading .property file:");
//            e.printStackTrace();
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

        builder.append(Constants.PROPERTY_INDEX +"="+book.getId()+"\n");
        builder.append(Constants.PROPERTY_AUTHOR+"="+book.getAuthor()+"\n");
        builder.append(Constants.PROPERTY_TITLE+"="+book.getTitle()+"\n");
        builder.append(Constants.PROPERTY_DATE+"="+ Librarians.convertDateToString(book.getDate())+"\n");
        builder.append(Constants.PROPERTY_SUBSCRIBER+"="+ book.getSubscriber() == null ? "" : book.getSubscriber());

        try(PrintWriter out = new PrintWriter(new File(filename).getAbsoluteFile())){
            out.print(builder.toString());
        } catch (IOException e) {
            System.out.println("Problem with writing to .property file:");
//            e.printStackTrace();
        }
    }



}
