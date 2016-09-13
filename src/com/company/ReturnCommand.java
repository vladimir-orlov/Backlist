package com.company;

import java.util.List;
import java.util.regex.Pattern;

public class ReturnCommand implements Command {
    @Override
    public void execute(List<String> params) {
        boolean flag = false;
        Librarians librarian = new Librarians();
        for(String param : params ){
            if(Pattern.matches("^id=\\d+$", param)){
                flag = true;
                librarian.returnBook(Integer.parseInt(param.split("=")[1]));
            }
        }
        if(flag == false){
            System.out.println(LocaleResource.getString("message.syntaxError"));
        }
    }
}
