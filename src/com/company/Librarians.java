package com.company;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class Librarians implements Librarian {
    /*
* in: FIND [author=<автор>] [name=<bookname>]
* out: FOUND id=<индекс1> lib=<библиотека1>
*      FOUNDMISSING id=<индекс1> lib=<библиотека1> issued=<дата выдачи1>
*      NOTFOUND
* */
    @Override
    public String findBook(String... params) {
        findBook(params[1].split("=")[1], params[2].split("=")[1]);
        return null;
    }

    /*
* in: ORDER id=<индекс> abonent=<имя абонента>
* out: OK abonent=<имя абонента> date= <текущая дата>
*      RESERVED abonent=<имя абонента> date= <текущая дата>
*      NOTFOUND
* */
    @Override
    public String orderBook(String... params) {


        return null;
    }

    /*
* in: RETURN id=<индекс>
* out: OK abonent=<имя абонента>
*      ALREADYRETURNED
*      NOTFOUND
* */
    @Override
    public String returnBook(String... params) {
        return null;
    }

    public static void findBook(String author, String name){
        File F = new File("Library");
        boolean find = false;
        ArrayList<Book> books = processFilesFromFolder(F,  new ArrayList<Book>());
        for(Book book : books) {
            if ( author.equals(book.getAuthor().trim()) && name.equals(book.getTitle().trim())) {
              if(book.getDate() == null){
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


    public static void orderBook(String id, String abonent){
       //TODO отправить путь в константы, продумать название библиотек

        File F = new File("Library");
        boolean find = false;
        ArrayList<Book> books = processFilesFromFolder(F,  new ArrayList<Book>());
        for(Book book : books) {
            if ( id.equals(book.getId())) {
                if(book.getDate() == null){
                    //TODO делаем заказ, сделать нормальный формат даты.



                    find = true;
                    System.out.println("OK " + abonent + new Date().toString());
                } else {
                    find = true;
                    System.out.println("RESERVED "+book.print(new String[]{"abonent"} + new Date().toString()));
                }
            }
        }
        if(!find){
            System.out.println("NOT FOUND");
        }
    }


    public static void returnBook(String id){
        File F = new File("Library");
        boolean find = false;
        ArrayList<Book> books = processFilesFromFolder(F,  new ArrayList<Book>());
        for(Book book : books) {
            if ( id.equals(book.getId())) {
                if(book.getDate() != null){
                    //TODO делаем возврат, сделать нормальный формат даты.



                    find = true;
                    System.out.println("OK " + book.print(new String[]{"abonent"}));
                } else {
                    find = true;
                    System.out.println("ALREADY RETURNED");
                }
            }
        }
        if(!find){
            System.out.println("NOT FOUND");
        }
    }

    public static ArrayList<Book> processFilesFromFolder(File folder, ArrayList<Book> books) {
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

    public static LibFactory createLibFactory(String typeFile){
        if(typeFile.equalsIgnoreCase("csv")){
            return new JavaCsvFactory();
        } else if(typeFile.equalsIgnoreCase("properties")){
            return new JavaPropertyFactory();
        } else {
            throw new RuntimeException(typeFile + " is unknown typeFile.");
        }
    }
}
