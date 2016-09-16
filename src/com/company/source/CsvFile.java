package com.company.source;

import com.company.core.Book;
import com.company.core.Librarians;
import com.company.core.LocaleResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CsvFile implements BaseBookWorker {
    static final Logger logger = LogManager.getLogger(CsvFile.class);
    @Override
    public List<Book> returnAllBook(File csvFile) {
        ArrayList<Book> books = new ArrayList<>();
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
             try {
                 StringTokenizer book = new StringTokenizer(line, cvsSplitBy);
                 books.add(new Book(csvFile.getParent(), Integer.parseInt(nextToken(book)), nextToken(book),
                         nextToken(book), Librarians.convertStringToDate(nextToken(book)), nextToken(book)));
             } catch (ParseException e){
                 logger.error(LocaleResource.getString("message.wrongDateCsv", csvFile.getName(), line));
             } catch (NumberFormatException e){
                 logger.error(LocaleResource.getString("message.wrongIdCsv", csvFile.getName(), line));
             }
            }
        } catch (IOException e) {
            logger.error(LocaleResource.getString("message.problemWithReading", csvFile.getName()));
        }
        return books;
    }

    private String nextToken(StringTokenizer tokenizer){
        return tokenizer.hasMoreElements() ? tokenizer.nextToken() : null;
    }

    @Override
    public void writeChanges(String filename, List<Book> books) {
        StringBuilder builder = new StringBuilder();
        for(Book book : books){
            builder.append(book.getId() + ",");
            builder.append(book.getAuthor() + ",");
            builder.append(book.getTitle() + ",");
            builder.append(Librarians.convertDateToString(book.getDate()) + ",");
            builder.append((book.getSubscriber() == null ? "" : book.getSubscriber()) + "\n");
        }

        try(PrintWriter out = new PrintWriter(new File(filename).getAbsoluteFile())){
            out.print(builder.toString());
        } catch (IOException e) {
            logger.error(LocaleResource.getString("message.problemWithWriting", filename));
        }
    }
}
