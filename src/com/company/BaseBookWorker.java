package com.company;

import java.io.File;
import java.util.List;

public interface BaseBookWorker {
    List<Book> returnAllBook(File title);
    void writeChanges(String filename, List<Book> books);
}
