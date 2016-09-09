package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Place for command(FIND, ORDER, RETURN, EXIT):");

        while(true) {
            Scanner in = new Scanner(System.in);
            String[] line = in.nextLine().split(" ");

            switch (line[0]){
                case "FIND":
                    if(line.length >1){
                        find(line[1], line[2]);
                    } else {
                        find(line[1]);

                    }
                    System.out.println("FIND");
                    break;
                case "ORDER":
                    System.out.println("ORDER");
                    break;
                case "RETURN":
                    System.out.println("RETURN");
                    break;
                case "EXIT":
                    System.out.println("EXIT");
                    return;
                default:
                    System.out.println("SYNTAXERROR");
                    break;

            }

        }
    }

    public static void find(String author, String name){
        File F = new File("Library");
        ArrayList<Book> books = processFilesFromFolder(F);
        for(Book book : books) {
            if (book.getAuthor().equals(author) && book.getTitle().equals(name)) {
                System.out.println("FOUND ");
                book.toStringWithLibrary();
            }
        }
    }

    public static void find(String param){
        File F = new File("Library");
        ArrayList<Book> books = processFilesFromFolder(F);
        if(param.contains("author=")){
            for(Book book : books) {
                if (book.getAuthor().equals(param)) {
                    System.out.println("FOUND ");
                    book.toStringWithLibrary();
                }
            }
        } else if(param.contains("name=")){
            for(Book book : books) {
                if (book.getTitle().equals(param)) {
                    System.out.println("FOUND ");
                    book.toStringWithLibrary();
                }
            }
        }
    }

    public static ArrayList<Book> processFilesFromFolder(File folder) {
        File[] folderEntries = folder.listFiles();
        ArrayList<Book> books = new ArrayList<>();
        for (File entry : folderEntries)
        {
            if (entry.isDirectory())
            {
                processFilesFromFolder(entry);
                continue;
            }
            LibFactory libFactory = createLibFactory(entry.getName().split(".")[1]);
            BaseBookWorker library = libFactory.createLib();
            books.addAll(library.returnAllBook(entry));
        }
        return books;
    }

    public static LibFactory createLibFactory(String typeFile){
        if(typeFile.equalsIgnoreCase("csv")){
            return new JavaCsvFactory();
        } else if(typeFile.equalsIgnoreCase("txt")){
            return new JavaPropertyFactory();
        } else {
            throw new RuntimeException(typeFile + " is unknown typeFile.");
        }
    }
}
