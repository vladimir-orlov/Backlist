package com.company;

import java.util.Map;

public class CommandInterpreter {
    private Command command;

    public void setCommand(Command command){
        this.command = command;
    }

    public void initCommand(Map<String,String> params){
        command.init(params);
    }

    public void executeCommand(){
        if(!command.verify()){
           //TODO - sout
            //TODO throw exception
            System.out.println(LocaleResource.getString("message.syntaxError"));
        } else {
            command.execute();
        }
    }
}
