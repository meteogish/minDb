package ConsoleEntry;

import com.google.inject.Injector;

import minDb.Core.Components.IMetaInfoRepository;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Factory.Container;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Container c = new Container();
        Injector i = c.getInjector();
        IMetaInfoRepository repo = i.getInstance(IMetaInfoRepository.class);
        try{
            DatabaseMetaInfo db = repo.getDatabaseMetaInfo("c:/Users/yevhenii.kyshko/out.json");
        }
        catch(ValidationException ex)
        {
            System.out.println(ex.getStackTrace());
        }
        // QueryParser q = new QueryParser();
        // q.parse("select Id as i, Another as c, count(POI) as co from Customers join Suppliers sup on sup.Id = c.id and sup.Id2 = c.Id2 where Id = 13 and Name = 'Zhenya' or Salary > 500");
        //q.parse("select * from Customers");
        //System.out.println( q.parse("afafs") == null);
    }
}
