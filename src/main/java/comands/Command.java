package comands;

import java.util.Map;

public abstract class Command {
    Map<String, String> params;
    void init(Map<String, String> params){
        this.params = params;
    }
    abstract boolean verify();
    abstract String execute() throws ExitException;

}
