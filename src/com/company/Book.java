package com.company;

import java.util.*;

public class Book {

    Book(String library, String file, int id, String author, String title, String date, String subscriber){
        this.library = library;
        this.id = id;
        this.author = author;
        this.title = title;
        this.date = date;
        this.subscriber = subscriber;
        this.file = file;
    }

    Book(String library, String file, int id, String author, String title){
        this(library, file, id, author, title, "", "");
    }

    private int id;
    private String author;
    private String title;
    private String date;
    private String subscriber;
    private String library;
    private String file;

    private Map getMapOfFields(){
        Map fields = new HashMap();
        fields.put("id", id);
        fields.put("name", author);
        fields.put("title", title);
        fields.put("issued", date);
        fields.put("abonent", subscriber);
        fields.put("lib", library);
        fields.put("file", file);
        return fields;
    }

    public String print(String... params){
        StringBuilder sb = new StringBuilder();
        Map map = new HashMap(getMapOfFields());
        for(String param : params){
            if(map.containsKey(param)){
                sb.append(param + "=" + map.get(param));
                sb.append(" ");
            } else {
                return "Wrong argument:" + param;
            }
        }
        return sb.toString();
    }

    public String toString(){
        return "lib:"+library+"; file:"+file+"; id:"+id+"; author:"+author+"; title:"+title+"; date:"+date+"; sub:"+subscriber;
    }

    //TODO correct list of setters and getters
    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public String getFile() {
        return file;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }
}
