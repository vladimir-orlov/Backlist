package com.company;

import java.util.List;

public class ExitCommand implements Command{
    @Override
    public void execute(List<String> params) {
        System.out.println(LocaleResource.getString("message.exit"));
        Runtime.getRuntime().exit(0);
    }
}
