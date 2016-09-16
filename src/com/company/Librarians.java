package com.company;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//loger + levels
//package
//Book - vision
//Exception for people
//Constants
//new parser csv

public class Librarians {
    static final Logger logger = LogManager.getLogger(Librarians.class.getName());

    public String findBook(String author, String name){
        File file = new File(Constants.PATH_TO_LIBRARY);
        ArrayList<Book> books = processFilesFromFolder(file,  new ArrayList<Book>());
        StringBuilder sb = new StringBuilder();

        for(Book book : books) {
            if (contains(book.getAuthor(), author) && contains(book.getTitle(), name)) {
              if(book.getDate() == null){
                  sb.append(String.format(LocaleResource.getString("message.found"), book.getId(), book.getLibrary()));
              } else {
                  sb.append(String.format(LocaleResource.getString("message.foundmissing"), book.getId(), book.getLibrary(), convertDateToString(book.getDate())));
                  logger.trace("Entering Log4j Example.");
                  logger.error("Entering Log4j Example.");
                  logger.entry("Entering Log4j Example.");
              }
            }
        }
        return sb.length() > 0 ? sb.toString() : LocaleResource.getString("message.notfound");
    }

    public String orderBook(int id, String abonent){
        File F = new File(Constants.PATH_TO_LIBRARY);
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
                        return String.format(LocaleResource.getString("message.orderOk"), abonent, convertDateToString(new Date()));
                    } else {
                        return String.format(LocaleResource.getString("message.reserved"), book.getSubscriber(), convertDateToString(new Date()));
                    }
                }
            }
        }
        return LocaleResource.getString("message.notfound");
    }

    public String returnBook(int id){
        File F = new File(Constants.PATH_TO_LIBRARY);
        ArrayList<String> files = returnAllFilesFromFolder(F, new ArrayList<>());
        for(String file : files) {
            LibFactory libFactory = createLibFactory(file);
            BaseBookWorker library = libFactory.createLib();
            List<Book> allBooks = library.returnAllBook(new File(file));
            for(Book book : allBooks){
                if (id == book.getId()) {
                    if(book.getDate() != null){
                        String sub = book.getSubscriber();
                        book.setSubscriber(null);
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
            //TODO custom exception for problems with reading file
            throw new RuntimeException(typeFile + " is unknown typeFile.");
        }
    }

    private boolean contains(String str, String searchString){
        return  searchString == null ? true : str.contains(searchString);
    }

    public static Date convertStringToDate(String str) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd");
        Date date = null;
        try {
            if(str != null && !str.isEmpty()) {
                date = dateFormatter.parse(str);
            }
        } catch (ParseException e){
            System.out.println(LocaleResource.getString("message.problemsParse"));
            e.printStackTrace();
        }
        return date;
    }
    public static String convertDateToString(Date date){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd");
        return date == null ? "" : dateFormatter.format(date);
    }
}
