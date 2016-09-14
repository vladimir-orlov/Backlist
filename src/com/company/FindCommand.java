package com.company;

public class FindCommand extends Command {
    @Override
    boolean verify() {
        return params.get("author") != null || params.get("name") != null;
    }

    @Override
    String execute() {
        Librarians librarian = new Librarians();
        return librarian.findBook(params.get("author"), params.get("name"));
    }
}
