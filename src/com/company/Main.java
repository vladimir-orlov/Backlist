package com.company;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        System.out.println("Place for command(FIND, ORDER, RETURN, EXIT):");
        try(Scanner in = new Scanner(System.in);) {
            while (true) {
                String console = in.nextLine();
                String[] line = console.split(" ");
                Librarians user = new Librarians("Library");

                if (Pattern.matches("^FIND (author|name)=\\w+$", console)) {
                    if("author".equals(line[1].split("=")[0])){
                        user.findBook(line[1].split("=")[1], "");
                    } else {
                        user.findBook("", line[1].split("=")[1]);
                    }
                } else if (Pattern.matches("^FIND author=\\w+ name=\\w+$", console)) {
                    user.findBook(line[1].split("=")[1], line[2].split("=")[1]);
                } else if (Pattern.matches("^ORDER id=\\d+ abonent=\\w+$", console)) {
                    user.orderBook(Integer.parseInt(line[1].split("=")[1]), line[2].split("=")[1]);
                } else if (Pattern.matches("^RETURN id=\\d+$", console)) {
                    user.returnBook(Integer.parseInt(line[1].split("=")[1]));
                } else if (Pattern.matches("^EXIT$", console)) {
                    return;
                } else {
                    System.out.println("SYNTAX ERROR");
                }
            }
        } catch (RuntimeException e){
            System.out.println("You should find the reason of this error:");
            e.printStackTrace();
        }
    }
}
