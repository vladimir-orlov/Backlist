package com.company;

public class ExitCommand extends Command {
    @Override
    boolean verify() {
        if(params.size() == 0){
            return true;
        }
        return false;
    }

    @Override
    String execute() {

        //TODO throw
        return "Exit";
    }
}
