package com.company;

import java.util.List;
import java.util.regex.Pattern;

public class FindCommand implements Command{
    @Override
    public void execute(List<String> params) {
        boolean flag = false;
        String author = "";
        String name = "";
        Librarians librarian = new Librarians();
        for(String param : params ){
            if(Pattern.matches("^author=\\w+$", param)){
                flag = true;
                author = param.split("=")[1];
            }
            if(Pattern.matches("^name=\\w+$", param)){
                flag = true;
                name = param.split("=")[1];
            }
        }

        if(flag == false){
            System.out.println(LocaleResource.getString("message.syntaxError"));
        } else {
            librarian.findBook(author, name);
        }
    }
}
