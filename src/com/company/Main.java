package com.company;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String,Command> mapOFCommands = new HashMap<>();
        mapOFCommands.put(Constants.FIND, new FindCommand());
        mapOFCommands.put(Constants.ORDER, new OrderCommand());
        mapOFCommands.put(Constants.RETURN, new ReturnCommand());
        mapOFCommands.put(Constants.EXIT, new ExitCommand());

        System.out.println(LocaleResource.getString("message.placeForCommand"));
        try(Scanner in = new Scanner(System.in);) {
            while (true) {
                try{
                    StringTokenizer line= new StringTokenizer(in.nextLine(), " ");
                    CommandInterpreter interpreter = new CommandInterpreter();
                    String request = line.nextToken();
                    if(mapOFCommands.get(request) == null){
                        throw new SyntaxException();
                    } else {
                        interpreter.setCommand(mapOFCommands.get(request));
                        Map<String, String> params = collectionParametersToMap(line);
                        interpreter.initCommand(params);
                        System.out.println(interpreter.executeCommand());
                    }
                } catch (SyntaxException e){
                    System.out.println(LocaleResource.getString("message.syntaxError"));
                }
                catch (NoSuchElementException e){
                    System.out.println(LocaleResource.getString("message.badPattern"));
                    e.printStackTrace();
            }
        }
        } catch (ExitException e){
            System.out.println(LocaleResource.getString("message.exit"));
            return;
        }
    }

    private static Map<String,String> collectionParametersToMap(StringTokenizer line) throws SyntaxException {
        Map<String, String> params = new HashMap<>();
        String [] param;
        while (line.hasMoreElements()) {
           try {
               param = line.nextToken().split("=");
               params.put(param[0], param[1]);
           } catch (ArrayIndexOutOfBoundsException e){
               throw new SyntaxException(param);
           }
        }
        return params;
    }
}
