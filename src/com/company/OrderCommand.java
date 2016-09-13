package com.company;

import java.util.List;
import java.util.regex.Pattern;

public class OrderCommand implements Command{
    @Override
    public void execute(List<String> params) {
        boolean flag = false;
        int id = 0;
        String abonent = null;
        Librarians librarian = new Librarians();
        for(String param : params ){
            if(Pattern.matches("^id=\\d+$", param)){
                flag = true;
                id = Integer.parseInt(param.split("=")[1]);
            }
            if(Pattern.matches("^abonent=\\w+$", param)){
                flag = true;
                abonent = param.split("=")[1];
            }
        }

        if(flag == false){
            System.out.println(LocaleResource.getString("message.syntaxError"));
        } else {
            librarian.orderBook(id, abonent);
        }
    }
}
