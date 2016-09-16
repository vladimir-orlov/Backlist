package com.company.comands;

import com.company.core.LocaleResource;

public class ExitCommand extends Command {
    @Override
    boolean verify() {
        return params.size() == 0 ;
    }

    @Override
    String execute() throws ExitException {
        throw new ExitException(LocaleResource.getString("message.exit"));
    }
}
