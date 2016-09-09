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
            Librarians user = new Librarians();
            switch (line[0]){
                case "FIND":
                    user.findBook(line);
                    break;
                case "ORDER":
                    user.orderBook(line);
                    break;
                case "RETURN":
                    user.returnBook(line);
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



//    public static void find(String param){
//        File F = new File("Library");
//        ArrayList<Book> books = processFilesFromFolder(F);
//        if(param.contains("author=")){
//            for(Book book : books) {
//                if (book.getAuthor().equals(param)) {
//                    System.out.println("FOUND ");
//                    book.toStringWithLibrary();
//                }
//            }
//        } else if(param.contains("name=")){
//            for(Book book : books) {
//                if (book.getTitle().equals(param)) {
//                    System.out.println("FOUND ");
//                    book.toStringWithLibrary();
//                }
//            }
//        }
//    }

//    public static ArrayList<Book> processFilesFromFolder(File folder) {
//        File[] folderEntries = folder.listFiles();
//        ArrayList<Book> books = new ArrayList<>();
//        for (File entry : folderEntries)
//        {
//            if (entry.isDirectory())
//            {
//                processFilesFromFolder(entry);
//                continue;
//            }
//            LibFactory libFactory = createLibFactory(entry.getName().split(".")[1]);
//            BaseBookWorker library = libFactory.createLib();
//            books.addAll(library.returnAllBook(entry));
//        }
//        return books;
//    }


}
