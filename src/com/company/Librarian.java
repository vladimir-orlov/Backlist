package com.company;

public interface Librarian {
    String findBook(String... params);

    String orderBook(String... params);

    String returnBook(String... params);
}
