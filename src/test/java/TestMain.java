import comands.SyntaxException;
import org.junit.*;

import java.util.Map;
import java.util.StringTokenizer;

import static org.junit.Assert.*;

public class TestMain {
    @Test(expected = SyntaxException.class)
    public void testExcepteionCollectionMethod() throws SyntaxException {
        StringTokenizer tokenizer = new StringTokenizer("FIND asd=");
        Main.collectionParametersToMap(tokenizer);
    }

    @Test
    public void testCollectionMethod() throws SyntaxException {
        StringTokenizer tokenizer = new StringTokenizer("author=Author name=Title");
        Map<String, String> map =  Main.collectionParametersToMap(tokenizer);
        assertEquals("Author", map.get("author"));
        assertEquals("Title", map.get("name"));
    }
}
