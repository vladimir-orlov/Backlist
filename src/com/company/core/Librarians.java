package com.company.core;

import com.company.source.*;
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

public class Librarians {
    static final Logger logger = LogManager.getLogger(Librarians.class);

    public String findBook(String author, String name){
        File file = new File(Constants.PATH_TO_LIBRARY);
        ArrayList<Book> books = processFilesFromFolder(file,  new ArrayList<Book>());
        StringBuilder sb = new StringBuilder();

        for(Book book : books) {
            if (containsSearchString(book.getAuthor(), author) && containsSearchString(book.getTitle(), name)) {
              if(book.getDate() == null){
                  sb.append(String.format(LocaleResource.getString("message.found"), book.getId(), book.getLibrary()));
              } else {
                  sb.append(String.format(LocaleResource.getString("message.foundmissing"), book.getId(), book.getLibrary(), convertDateToString(book.getDate())));
              }
            }
        }
        return sb.length() > 0 ? sb.toString() : LocaleResource.getString("message.notfound");
    }

    public String orderBook(int id, String abonent){
        File F = new File(Constants.PATH_TO_LIBRARY);
        ArrayList<String> files = returnAllFilesFromFolder(F,  new ArrayList<String>());
        for(String file : files) {
            try {
                LibFactory libFactory = createLibFactory(file);
                BaseBookWorker library = libFactory.createLib();
                List<Book> allBooks = library.returnAllBook(new File(file));
                for (Book book : allBooks) {
                    if (id == book.getId()) {
                        if (book.getDate() == null) {
                            book.setSubscriber(abonent);
                            book.setDate(new Date());
                            library.writeChanges(file, allBooks);
                            return String.format(LocaleResource.getString("message.orderOk"), abonent, convertDateToString(new Date()));
                        } else {
                            return String.format(LocaleResource.getString("message.reserved"), book.getSubscriber(), convertDateToString(new Date()));
                        }
                    }
                }
            } catch (FileExtensionException e){
                logger.error(e.getMessage());
            }
        }
        return LocaleResource.getString("message.notfound");
    }

    public String returnBook(int id){
        File F = new File(Constants.PATH_TO_LIBRARY);
        ArrayList<String> files = returnAllFilesFromFolder(F, new ArrayList<>());
        for(String file : files) {
           try {
               LibFactory libFactory = createLibFactory(file);
               BaseBookWorker library = libFactory.createLib();
               List<Book> allBooks = library.returnAllBook(new File(file));
               for (Book book : allBooks) {
                   if (id == book.getId()) {
                       if (book.getDate() != null) {
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
           } catch (FileExtensionException e){
               logger.error(e.getMessage());
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
            try {
                LibFactory libFactory = createLibFactory(entry.getName());
                BaseBookWorker library = libFactory.createLib();
                books.addAll(library.returnAllBook(entry));
            } catch (FileExtensionException e) {
                logger.error(e.getMessage());
            }
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

    private LibFactory createLibFactory(String file) throws FileExtensionException {
        Pattern p = Pattern.compile("\\.\\w+$");
        Matcher matcher = p.matcher(file);
        matcher.find();
        String typeFile = matcher.group();

        if(typeFile.equalsIgnoreCase(Constants.EXTENSION_CSV)){
            return new JavaCsvFactory();
        } else if(typeFile.equalsIgnoreCase(Constants.EXTENSION_PROPERTY)){
            return new JavaPropertyFactory();
        } else {
            throw new FileExtensionException(String.format(LocaleResource.getString("message.unknownType"), file));
        }
    }

    private boolean containsSearchString(String str, String searchString){
        return  searchString == null ? true : str.contains(searchString);
    }

    public static Date convertStringToDate(String str) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd");
        return (str == null || str.isEmpty()) ? null : dateFormatter.parse(str);
    }

    public static String convertDateToString(Date date){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd");
        return date == null ? "" : dateFormatter.format(date);
    }
}
