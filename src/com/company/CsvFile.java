package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvFile implements BaseBookWorker {
    @Override
    public List<Book> returnAllBook(File csvFile) {
        ArrayList<Book> books = new ArrayList<>();
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] book = line.split(cvsSplitBy);
                if(book.length > 3){
                    books.add(new Book(csvFile.getParent(), csvFile.getName(), Integer.parseInt(book[0]), book[1], book[2], book[3], book[4]));
                } else {
                    books.add(new Book(csvFile.getParent(), csvFile.getName(), Integer.parseInt(book[0]), book[1], book[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public void writeChanges(String filename, List<Book> books) {
        StringBuilder builder = new StringBuilder();
        for(Book book : books){
            builder.append(book.getId() + ",");
            builder.append(book.getAuthor() + ",");
            builder.append(book.getTitle() + ",");
            builder.append(book.getDate() + ",");
            builder.append(book.getSubscriber() + "\n");
        }

        TextFile.write(filename, builder.toString());
    }
}
