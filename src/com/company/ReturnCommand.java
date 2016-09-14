package com.company;

public class ReturnCommand extends Command {
    @Override
    boolean verify() {
        return params.get("id") != null;
    }

    @Override
    String execute() {
        Librarians librarian = new Librarians();
        return librarian.returnBook(Integer.parseInt(params.get("id")));
    }
}
