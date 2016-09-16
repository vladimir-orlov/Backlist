package com.company.comands;

import com.company.core.Constants;
import com.company.core.Librarians;

public class FindCommand extends Command {
    @Override
    boolean verify() {
        return params.get(Constants.BOOK_AUTHOR) != null || params.get(Constants.BOOK_TITLE) != null;
    }

    @Override
    String execute() {
        Librarians librarian = new Librarians();
        return librarian.findBook(params.get(Constants.BOOK_AUTHOR), params.get(Constants.BOOK_TITLE));
    }
}
