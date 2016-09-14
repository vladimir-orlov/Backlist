package com.company;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String,Command> mapOFCommands = new HashMap<>();
        mapOFCommands.put(Consts.FIND, new FindCommand());
        mapOFCommands.put(Consts.ORDER, new OrderCommand());
        mapOFCommands.put(Consts.RETURN, new ReturnCommand());
        mapOFCommands.put(Consts.EXIT, new ExitCommand());

        System.out.println(LocaleResource.getString("message.placeForCommand"));
        try(Scanner in = new Scanner(System.in);) {
            while (true) {
                StringTokenizer line= new StringTokenizer(in.nextLine(), " ");
                CommandInterpreter interpreter = new CommandInterpreter();
                String request = line.nextToken();
                if(mapOFCommands.get(request) == null){
                    System.out.println(LocaleResource.getString("message.syntaxError"));
                } else {
                    interpreter.setCommand(mapOFCommands.get(request));
                    Map<String, String> params = new HashMap<>();
                    String [] param;
                    while (line.hasMoreElements()) {
                        param = line.nextToken().split("=");
                        params.put(param[0], param[1]);
                    }
                    interpreter.initCommand(params);
                    interpreter.executeCommand();
                }
            }
        } catch (InputMismatchException e){
            System.out.println(LocaleResource.getString("message.badPattern"));
            e.printStackTrace();
        }
    }
}
