package com.company;

import java.util.List;

public class Commands {
    private Command command;

    public void setCommand(Command command){
        this.command = command;
    }

    public void executeCommand(List<String> params){
        command.execute(params);
    }
}
