package comands;

import core.Constants;
import core.Librarians;
import core.LocaleResource;

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

    @Override
    public String toString() {
        return LocaleResource.getString("command.find");
    }
}
