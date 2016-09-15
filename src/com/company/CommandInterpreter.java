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

    public String executeCommand() throws SyntaxException, ExitException {
        if(command.verify()){
            return command.execute();
        } else {
            throw new SyntaxException();
        }
    }
}
