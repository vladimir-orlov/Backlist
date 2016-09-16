package com.company.core;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleResource {
    private Locale locale;
    private ResourceBundle res;
    private static LocaleResource resource;
    private LocaleResource(){
        locale  = new Locale("en_us");
        res = ResourceBundle.getBundle("resources/data", locale);
    };

    public static String getString(String key){
        if(resource == null){
            resource = new LocaleResource();
        }
        return resource.res.getString(key);
    }

    public static String getString(String key, Object... args){
        return String.format(key, args);
    }
}
