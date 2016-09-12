package com.company;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            if ( author.equals(book.getAuthor().trim()) && name.equals(book.getTitle().trim())) {
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
                    LibFactory libFactory = createLibFactory(book.getFile().split("\\.")[1]);
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
                    LibFactory libFactory = createLibFactory(book.getFile().split("\\.")[1]);
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
            LibFactory libFactory = createLibFactory(entry.getName().split("\\.")[1]);
            BaseBookWorker library = libFactory.createLib();
            books.addAll(library.returnAllBook(entry));
        }
        return books;
    }

    private LibFactory createLibFactory(String typeFile){
        if(typeFile.equalsIgnoreCase("csv")){
            return new JavaCsvFactory();
        } else if(typeFile.equalsIgnoreCase("properties")){
            return new JavaPropertyFactory();
        } else {
            throw new RuntimeException(typeFile + " is unknown typeFile.");
        }
    }
}
