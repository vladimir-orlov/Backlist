package com.company;

public class OrderCommand extends Command {
    @Override
    boolean verify() {
        return params.get("id") != null && params.get("abonent") != null;
    }

    @Override
    String execute() {
        Librarians librarian = new Librarians();
        return librarian.orderBook(Integer.parseInt(params.get("id")), params.get("abonent"));
    }
}
