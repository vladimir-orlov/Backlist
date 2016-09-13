package com.company;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String,Command> mapOFCommands = new HashMap<>();
        mapOFCommands.put("FIND", new FindCommand());
        mapOFCommands.put("ORDER", new OrderCommand());
        mapOFCommands.put("RETURN", new ReturnCommand());
        mapOFCommands.put("EXIT", new ExitCommand());


        System.out.println(LocaleResource.getString("message.placeForCommand"));
        try(Scanner in = new Scanner(System.in);) {
            while (true) {
                StringTokenizer line= new StringTokenizer(in.nextLine(), " ");
                Commands command = new Commands();
                String request = line.nextToken();
                if(mapOFCommands.get(request) == null){
                    System.out.println(LocaleResource.getString("message.syntaxError"));
                } else {
                    command.setCommand(mapOFCommands.get(request));
                    List<String> params = new ArrayList<>();
                    while (line.hasMoreElements()) {
                        params.add(line.nextToken());
                    }
                    command.executeCommand(params);
                }
            }
        } catch (InputMismatchException e){
            System.out.println(LocaleResource.getString("message.badPattern"));
            e.printStackTrace();
        }
    }
}
