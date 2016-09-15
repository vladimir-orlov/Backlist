package com.company;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                if(book.length == 5){
                    books.add(new Book(csvFile.getParent(), Integer.parseInt(book[0]), book[1], book[2], Librarians.convertStringToDate(book[3]), book[4]));
                } else if(book.length == 3){
                    books.add(new Book(csvFile.getParent(), Integer.parseInt(book[0]), book[1], book[2]));
                } else {
                    System.out.println("File " + csvFile.getName() + " have wrong structure in line:\n" + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Problem with reading file:" + csvFile.getName());
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
            builder.append(Librarians.convertDateToString(book.getDate()) + ",");
            builder.append((book.getSubscriber() == null ? "" : book.getSubscriber()) + "\n");
        }

        try(PrintWriter out = new PrintWriter(new File(filename).getAbsoluteFile())){
            out.print(builder.toString());
        } catch (IOException e) {
            System.out.println("Problem with writing to file:\n" + e.toString());
        }
    }
}
