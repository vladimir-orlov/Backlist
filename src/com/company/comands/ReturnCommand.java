package com.company.comands;

import com.company.core.Constants;
import com.company.core.Librarians;
import com.company.core.LocaleResource;

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

    @Override
    public String toString() {
        return LocaleResource.getString("command.return");
    }
}
