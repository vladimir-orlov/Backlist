package com.company;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Librarians {
    private final String pathToLibrary;

    public Librarians(String pathToLibrary) {
        this.pathToLibrary = pathToLibrary;
    }

    public void findBook(String author, String name){
        File F = new File(pathToLibrary);
        boolean find = false;
        ArrayList<Book> books = processFilesFromFolder(F,  new ArrayList<Book>());
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

    public void findBook(boolean author, String param){
        File F = new File(pathToLibrary);
        boolean find = false;
        ArrayList<Book> books = processFilesFromFolder(F, new ArrayList<Book>());
        for(Book book : books) {
            if (author ? book.getAuthor().contains(param) : book.getTitle().contains(param)) {
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
        File F = new File(pathToLibrary);
        boolean find = false;
        ArrayList<Book> books = processFilesFromFolder(F,  new ArrayList<Book>());
        for(Book book : books) {
            if (id == book.getId()) {
                if(book.getDate().isEmpty()){
                    LibFactory libFactory = createLibFactory(book.getFile());
                    BaseBookWorker library = libFactory.createLib();
                    List<Book> allBooks = library.returnAllBook(new File(book.getFile()));
                    for(Book myBook : allBooks){
                        if(id == myBook.getId()){
                            myBook.setSubscriber(abonent);
                            myBook.setDate(formatter.format(new Date()));
                            break;
                        }
                    }
                    library.writeChanges(book.getFile(), allBooks);
                    find = true;
                    System.out.println("OK " + "abonent=" + abonent + " date=" + formatter.format(new Date()));
                } else {
                    find = true;
                    System.out.println("RESERVED "+ "abonent=" + book.getSubscriber() + " date=" + formatter.format(new Date()));
                }
                break;
            }
        }
        if(!find){
            System.out.println("NOT FOUND");
        }
    }

    public void returnBook(int id){
        File F = new File(pathToLibrary);
        boolean find = false;
        ArrayList<Book> books = processFilesFromFolder(F,  new ArrayList<Book>());
        for(Book book : books) {
            if ( id == book.getId()) {
                if(!book.getDate().isEmpty()){
                    LibFactory libFactory = createLibFactory(book.getFile());
                    BaseBookWorker library = libFactory.createLib();
                    List<Book> allBooks = library.returnAllBook(new File(book.getFile()));
                    for(Book myBook : allBooks){
                        if(id == myBook.getId()){
                            myBook.setSubscriber("");
                            myBook.setDate("");
                            break;
                        }
                    }
                    library.writeChanges(book.getFile(), allBooks);
                    find = true;
                    System.out.println("OK " + book.print(new String[]{"abonent"}));
                } else {
                    find = true;
                    System.out.println("ALREADY RETURNED");
                }
                break;
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