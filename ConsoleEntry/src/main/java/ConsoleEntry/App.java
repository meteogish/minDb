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
        q.parse("select Id as i, Another as c, count(POI) as co from Customers join Suppliers sup on sup.Id = c.id and sup.Id2 = c.Id2 where Id = 13 and Name = 'Zhenya' or Salary > 500");
        //q.parse("select * from Customers");
        //System.out.println( q.parse("afafs") == null);
    }
}
