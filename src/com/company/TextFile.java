package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TextFile  extends ArrayList<String>{
    //чтение файла как одной строки
    public static String read(String fileName){
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader in = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()));
            try{
                String s;
                while((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    //запись файла за один вызов метода
    public static void write(String filename, String text) {
        try{
            PrintWriter out = new PrintWriter(new File(filename).getAbsoluteFile());
            try{
                out.print(text);

            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //чтение файла с разбиением по регулярному выражению
    public TextFile(String fileName, String splitter){
        super(Arrays.asList(read(fileName).split(splitter)));
        if(get(0).equals(" ")) remove(0);
    }

    public TextFile(String fileName) {
        this (fileName, "\n");
    }

    public void write(String fileName) {
        try {
            PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile());
            try{
                for(String item : this)
                    out.println(item);
            } finally {
               out.close();
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
