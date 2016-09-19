package comands;

import core.Constants;
import core.Librarians;
import core.LocaleResource;

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

    @Override
    public String toString() {
        return LocaleResource.getString("command.order");
    }
}
