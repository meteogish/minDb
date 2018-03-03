package ConsoleEntry;

import minDb.SqlQueryParser.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        QueryParser q = new QueryParser();
        q.parse("afaf");
        System.out.println( q.parse("afafs") == null);
    }
}
