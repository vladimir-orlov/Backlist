package com.company;

public class OrderCommand extends Command {
    @Override
    boolean verify() {
        return params.get(Constants.BOOK_ID) != null && params.get(Constants.BOOK_SUBSCRIBER) != null;
    }

    @Override
    String execute() {
        Librarians librarian = new Librarians();
        return librarian.orderBook(Integer.parseInt(params.get(Constants.BOOK_ID)), params.get(Constants.BOOK_SUBSCRIBER));
    }
}
