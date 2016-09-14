package com.company;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Возвращать значения
//Константы
//Вынести методы
//Выкидывать ошибку
//Совершенный код

public class Librarians {
    private static final String PATH_TO_LIBRARY = "Library";

    public String findBook(String author, String name){
        File file = new File(PATH_TO_LIBRARY);
        ArrayList<Book> books = processFilesFromFolder(file,  new ArrayList<Book>());

        for(Book book : books) {
            if (contains(book.getAuthor(), author) && contains(book.getTitle(), name)) {
              if(book.getDate() == null){
                  return String.format(LocaleResource.getString("message.found"), book.getId(), book.getLibrary());
              } else {
                  return String.format(LocaleResource.getString("message.foundmissing"), book.getId(), book.getLibrary(), book.getDate());
              }
            }
        }
        return String.format(LocaleResource.getString("message.notfound"));
    }

    public String orderBook(int id, String abonent){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        File F = new File(PATH_TO_LIBRARY);
        ArrayList<String> files = returnAllFilesFromFolder(F,  new ArrayList<String>());
        for(String file : files) {
            LibFactory libFactory = createLibFactory(file);
            BaseBookWorker library = libFactory.createLib();
            List<Book> allBooks = library.returnAllBook(new File(file));
            for(Book book : allBooks){
                if (id == book.getId()) {
                    if(book.getDate() == null){
                        book.setSubscriber(abonent);
                        book.setDate(new Date());
                        library.writeChanges(file, allBooks);
                        return String.format(LocaleResource.getString("message.orderOk"), abonent, formatter.format(new Date()));
                    } else {
                        return String.format(LocaleResource.getString("message.reserved"), book.getSubscriber(), formatter.format(new Date()));
                    }
                }
            }
        }
        return LocaleResource.getString("message.notfound");
    }

    public String returnBook(int id){
        File F = new File(PATH_TO_LIBRARY);
        ArrayList<String> files = returnAllFilesFromFolder(F, new ArrayList<>());
        for(String file : files) {
            LibFactory libFactory = createLibFactory(file);
            BaseBookWorker library = libFactory.createLib();
            List<Book> allBooks = library.returnAllBook(new File(file));
            for(Book book : allBooks){
                if (id == book.getId()) {
                    if(book.getDate() != null){
                        String sub = book.getSubscriber();
                        book.setSubscriber("");
                        book.setDate(null);
                        library.writeChanges(file, allBooks);
                        return String.format(LocaleResource.getString("message.returnOk"), sub);
                    } else {
                        return LocaleResource.getString("message.alreadyReturned");
                    }
                }
            }
        }
        return LocaleResource.getString("message.notfound");
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

    private boolean contains(String str, String searchString){
        if(searchString == null){
            return true;
        }
        return str.contains(searchString);
    }
}
