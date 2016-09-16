package com.company.source;

public class JavaCsvFactory implements LibFactory {
    @Override
    public BaseBookWorker createLib() {
        return new CsvFile();
    }
}
