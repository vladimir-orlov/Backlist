package com.company;

public class ReturnCommand extends Command {
    @Override
    boolean verify() {
        return params.get(Constants.BOOK_ID) != null;
    }

    @Override
    String execute() {
        Librarians librarian = new Librarians();
        return librarian.returnBook(Integer.parseInt(params.get(Constants.BOOK_ID)));
    }
}
