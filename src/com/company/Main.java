package com.company;

import com.company.comands.*;
import com.company.core.Constants;
import com.company.core.Librarians;
import com.company.core.LocaleResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        final Logger logger = LogManager.getLogger(Librarians.class);

        Map<String,Command> mapOFCommands = new HashMap<>();
        mapOFCommands.put(Constants.FIND, new FindCommand());
        mapOFCommands.put(Constants.ORDER, new OrderCommand());
        mapOFCommands.put(Constants.RETURN, new ReturnCommand());
        mapOFCommands.put(Constants.EXIT, new ExitCommand());

       logger.info(LocaleResource.getString("message.placeForCommand"));
        try(Scanner in = new Scanner(System.in)) {
            while (true) {
                try{
                    StringTokenizer line= new StringTokenizer(in.nextLine(), " ");
                    CommandInterpreter interpreter = new CommandInterpreter();
                    String request = line.nextToken();
                    if(mapOFCommands.get(request) == null){
                        throw new SyntaxException(LocaleResource.getString("message.badPattern"));
                    } else {
                        interpreter.setCommand(mapOFCommands.get(request));
                        Map<String, String> params = collectionParametersToMap(line);
                        interpreter.initCommand(params);
                        System.out.println(interpreter.executeCommand());
                    }
                } catch (SyntaxException e){
                    System.out.println(e.getMessage());
                }
                catch (NoSuchElementException e){
                    System.out.println(LocaleResource.getString("message.badPattern"));
            }
        }
        } catch (ExitException e){
            logger.info(LocaleResource.getString("message.exit"));
            return;
        } catch (Throwable e) {
            logger.debug(LocaleResource.getString("unexpectedTermination"), e);
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
               throw new SyntaxException(LocaleResource.getString("message.badPatternCommand"));
           }
        }
        return params;
    }
}
