package source;

public class JavaBaseFactory implements LibFactory {
    @Override
    public BaseBookWorker createLib() {
        return new DataBase();
    }
}
