import comands.*;
import core.Librarians;
import core.LocaleResource;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestCommandInterpreter {

    @Test(expected = SyntaxException.class)
    public void testFindCommand() throws SyntaxException, ExitException {
        CommandInterpreter interpreter = new CommandInterpreter();
        interpreter.setCommand(new FindCommand());
        Map<String, String> params = new HashMap<>();
        params.put("author", "TestAuthor1");
        params.put("name", "TestBook1");
        interpreter.initCommand(params);
        assertEquals(LocaleResource.getString("message.foundmissing", 3000, "library\\Test_Library", "2014.12.10"), interpreter.executeCommand());
        params.clear();
        params.put("author", "TestAuthor2");
        params.put("name", "TestBook2");
        interpreter.initCommand(params);
        assertEquals(LocaleResource.getString("message.found", 3001, "library\\Test_Library"), interpreter.executeCommand());
        params.clear();
        params.put("author", "TestAuthor2");
        params.put("name", "TestBook2");
        interpreter.initCommand(params);
        assertEquals(LocaleResource.getString("message.found", 3001, "library\\Test_Library"), interpreter.executeCommand());
        params.clear();
        params.put("trash", "trash");
        interpreter.initCommand(params);
        interpreter.executeCommand();
    }

    @Test(expected = SyntaxException.class)
    public void testOrderCommand() throws SyntaxException, ExitException {
        CommandInterpreter interpreter = new CommandInterpreter();
        interpreter.setCommand(new OrderCommand());
        Map<String, String> params = new HashMap<>();
        params.put("id", "3001");
        params.put("abonent", "Abonent");
        interpreter.initCommand(params);
        assertEquals(LocaleResource.getString("message.orderOk", "Abonent", Librarians.convertDateToString(new Date())), interpreter.executeCommand());
        params.clear();
        params.put("id", "3001");
        params.put("abonent", "Reserved");
        interpreter.initCommand(params);
        assertEquals(LocaleResource.getString("message.reserved", "Abonent", Librarians.convertDateToString(new Date())), interpreter.executeCommand());
        params.clear();
        params.put("id", "4000");
        params.put("abonent", "notfound");
        interpreter.initCommand(params);
        assertEquals(LocaleResource.getString("message.notfound"), interpreter.executeCommand());
        params.clear();
        params.put("trash", "trash");
        interpreter.initCommand(params);
        interpreter.executeCommand();
    }

    @Test(expected = SyntaxException.class)
    public void testReturnCommand() throws SyntaxException, ExitException {
        CommandInterpreter interpreter = new CommandInterpreter();
        interpreter.setCommand(new ReturnCommand());
        Map<String, String> params = new HashMap<>();
        params.put("id", "3001");
        interpreter.initCommand(params);
        assertEquals(LocaleResource.getString("message.returnOk", "Abonent"), interpreter.executeCommand());
        params.clear();
        params.put("id", "3001");
        interpreter.initCommand(params);
        assertEquals(LocaleResource.getString("message.alreadyReturned"), interpreter.executeCommand());
        params.clear();
        params.put("id", "4000");
        interpreter.initCommand(params);
        assertEquals(LocaleResource.getString("message.notfound"), interpreter.executeCommand());
        params.clear();
        params.put("trash", "trash");
        interpreter.initCommand(params);
        interpreter.executeCommand();
    }

    @Test(expected = SyntaxException.class)
    public void testExitCommandSyntaxException() throws SyntaxException, ExitException {
        CommandInterpreter interpreter = new CommandInterpreter();
        interpreter.setCommand(new ExitCommand());
        Map<String, String> params = new HashMap<>();
        params.put("id", "3001");
        interpreter.initCommand(params);
        interpreter.executeCommand();
    }

    @Test(expected = ExitException.class)
    public void testExitCommandExitException() throws SyntaxException, ExitException {
        CommandInterpreter interpreter = new CommandInterpreter();
        interpreter.setCommand(new ExitCommand());
        interpreter.initCommand(new HashMap<>());
        interpreter.executeCommand();
    }
}
