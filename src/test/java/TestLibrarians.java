import core.Librarians;
import core.LocaleResource;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TestLibrarians {
    @Test
    public void testFindBook(){
        Librarians librarians = new Librarians();
        assertEquals(LocaleResource.getString("message.notfound"), librarians.findBook("notfound", "notfound"));
        assertEquals(LocaleResource.getString("message.foundmissing", 3000, "library\\Test_Library", "2014.12.10"), librarians.findBook("TestAuthor1", "TestBook1"));
        assertEquals(LocaleResource.getString("message.found", 3001, "library\\Test_Library"), librarians.findBook("TestAuthor2", "TestBook2"));
    }

    @Test
    public void testOrderBook(){
        Librarians librarians = new Librarians();
        assertEquals(LocaleResource.getString("message.notfound"), librarians.orderBook(4000, "notfound"));
        assertEquals(LocaleResource.getString("message.orderOk", "Abonent", Librarians.convertDateToString(new Date())), librarians.orderBook(3001, "Abonent"));
        assertEquals(LocaleResource.getString("message.reserved", "Abonent", Librarians.convertDateToString(new Date())), librarians.orderBook(3001, "Reserved"));

    }

    @Test
    public void testReturnBook(){
        Librarians librarians = new Librarians();
        assertEquals(LocaleResource.getString("message.notfound"), librarians.returnBook(4000));
        assertEquals(LocaleResource.getString("message.returnOk", "Abonent"), librarians.returnBook(3001));
        assertEquals(LocaleResource.getString("message.alreadyReturned"), librarians.returnBook(3001));
    }
}
