package com.company;

public class JavaPropertyFactory implements LibFactory{

    @Override
    public BaseBookWorker createLib() {
        return new PropertyFile();
    }
}
