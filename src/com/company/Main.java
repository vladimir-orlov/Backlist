package com.company;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String,Command> mapOFCommands = new HashMap<>();
        mapOFCommands.put(LocaleResource.getString("command.find"), new FindCommand());
        mapOFCommands.put(LocaleResource.getString("command.order"), new OrderCommand());
        mapOFCommands.put(LocaleResource.getString("command.return"), new ReturnCommand());
        mapOFCommands.put(LocaleResource.getString("command.exit"), new ExitCommand());


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
