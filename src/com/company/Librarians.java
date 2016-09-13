package com.company;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Librarians {
    private static final String PATH_TO_LIBRARY = "Library";

    public void findBook(String author, String name){
        File file = new File(PATH_TO_LIBRARY);
        boolean find = false;
        ArrayList<Book> books = processFilesFromFolder(file,  new ArrayList<Book>());
        for(Book book : books) {
            if (book.getAuthor().contains(author) && book.getTitle().contains(name)) {
              if(book.getDate().isEmpty()){
                  find = true;
                  System.out.println("FOUND " + book.print(new String[]{"id", "lib"}));
              } else {
                  find = true;
                  System.out.println("FOUNDMISSING "+book.print(new String[]{"id", "lib", "issued"}));
              }
            }
        }
        if(!find){
            System.out.println("NOT FOUND");
        }
    }

    public void orderBook(int id, String abonent){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        File F = new File(PATH_TO_LIBRARY);
        boolean find = false;
        ArrayList<String> files = returnAllFilesFromFolder(F,  new ArrayList<String>());
        a: for(String file : files) {
            LibFactory libFactory = createLibFactory(file);
            BaseBookWorker library = libFactory.createLib();
            List<Book> allBooks = library.returnAllBook(new File(file));
            for(Book book : allBooks){
                if (id == book.getId()) {
                    if(book.getDate().isEmpty()){
                        book.setSubscriber(abonent);
                        book.setDate(formatter.format(new Date()));
                        library.writeChanges(file, allBooks);
                        find = true;
                        System.out.println("OK " + "abonent=" + abonent + " date=" + formatter.format(new Date()));
                    } else {
                        find = true;
                        System.out.println("RESERVED "+ "abonent=" + book.getSubscriber() + " date=" + formatter.format(new Date()));
                    }
                    break a;
                }
            }
        }
        if(!find){
            System.out.println("NOT FOUND");
        }
    }

    public void returnBook(int id){
        File F = new File(PATH_TO_LIBRARY);
        boolean find = false;
        ArrayList<String> files = returnAllFilesFromFolder(F, new ArrayList<>());
        a: for(String file : files) {
            LibFactory libFactory = createLibFactory(file);
            BaseBookWorker library = libFactory.createLib();
            List<Book> allBooks = library.returnAllBook(new File(file));
            for(Book book : allBooks){
                if (id == book.getId()) {
                    if(!book.getDate().isEmpty()){
                        System.out.println("OK " + book.print(new String[]{"abonent"}));
                        book.setSubscriber("");
                        book.setDate("");
                        library.writeChanges(file, allBooks);
                        find = true;
                    } else {
                        find = true;
                        System.out.println("ALREADY RETURNED");
                    }
                    break a;
                }
            }
        }
        if(!find){
            System.out.println("NOT FOUND");
        }
    }

    private ArrayList<Book> processFilesFromFolder(File folder, ArrayList<Book> books) {
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries)
        {
            if (entry.isDirectory())
            {
                processFilesFromFolder(entry, books);
                continue;
            }
            LibFactory libFactory = createLibFactory(entry.getName());
            BaseBookWorker library = libFactory.createLib();
            books.addAll(library.returnAllBook(entry));
        }
        return books;
    }

    private ArrayList<String> returnAllFilesFromFolder(File folder, ArrayList<String> files) {
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries)
        {
            if (entry.isDirectory())
            {
                returnAllFilesFromFolder(entry, files);
                continue;
            }
            files.add(entry.getAbsolutePath());
        }
        return files;
    }

    private LibFactory createLibFactory(String file){
        Pattern p = Pattern.compile("\\.\\w+$");
        Matcher matcher = p.matcher(file);
        matcher.find();
        String typeFile = matcher.group();

        if(typeFile.equalsIgnoreCase(".csv")){
            return new JavaCsvFactory();
        } else if(typeFile.equalsIgnoreCase(".properties")){
            return new JavaPropertyFactory();
        } else {
            throw new RuntimeException(typeFile + " is unknown typeFile.");
        }
    }
}
