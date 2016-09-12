package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Place for command(FIND, ORDER, RETURN, EXIT):");

        while(true) {
            Scanner in = new Scanner(System.in);
            String[] line = in.nextLine().split(" ");
            Librarians user = new Librarians("Library");
            try {
                switch (line[0]) {
                /*
                * in: FIND [author=<автор>] [name=<bookname>]
                * out: FOUND id=<индекс1> lib=<библиотека1>
                *      FOUNDMISSING id=<индекс1> lib=<библиотека1> issued=<дата выдачи1>
                *      NOTFOUND
                * */
                    case "FIND":
                        user.findBook(line[1].split("=")[1], line[2].split("=")[1]);
                        break;
                /*
                * in: ORDER id=<индекс> abonent=<имя абонента>
                * out: OK abonent=<имя абонента> date= <текущая дата>
                *      RESERVED abonent=<имя абонента> date= <текущая дата>
                *      NOTFOUND
                * */
                    case "ORDER":
                        user.orderBook(Integer.parseInt(line[1].split("=")[1]), line[2].split("=")[1]);
                        break;
                /*
                * in: RETURN id=<индекс>
                * out: OK abonent=<имя абонента>
                *      ALREADYRETURNED
                *      NOTFOUND
                * */
                    case "RETURN":
                        user.returnBook(Integer.parseInt(line[1].split("=")[1]));
                        break;
                    case "EXIT":
                        System.out.println("EXIT");
                        return;
                    default:
                        System.out.println("SYNTAXERROR");
                        break;
                }
            } catch (RuntimeException e){
                System.out.println("SYNTAXERROR");
            }
        }
    }


//TODO rewrite method
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
}
