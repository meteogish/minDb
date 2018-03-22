package ConsoleEntry;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Injector;

import minDb.Core.Components.IMetaInfoRepository;
import minDb.Core.Components.IQueryParser;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Query;
import minDb.Core.QueryModels.Query.QueryType;
import minDb.Factory.Container;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        String dbFolder = "c:/Users/yevhenii.kyshko/Desktop/MinDb/Db/";
        
        Container c = new Container();
        Injector i = c.getInjector();
        IMetaInfoRepository repo = i.getInstance(IMetaInfoRepository.class);


        // IQueryParser parser = i.getInstance(IQueryParser.class);
        // Query query = parser.parse("create table Accounts(Id integer, Name varchar(10), Surname varchar(20))");
        // if (query.get_type() == QueryType.CreateTable) {
        //     TableMetaInfo createTable = query.get_createTableInfo();
        //     List<TableMetaInfo> listTables = new ArrayList<TableMetaInfo>(1);
        //     listTables.add(createTable);
        //     DatabaseMetaInfo db = new DatabaseMetaInfo(listTables);
        //     repo.saveDatabaseMetaInfo(db, "c:/Users/yevhenii.kyshko/Desktop/MinDb", "first");
        // }
        try {
			DatabaseMetaInfo db = repo.getDatabaseMetaInfo(Paths.get(dbFolder, "first.db").toString());
            POC_DataReader dataReader = new POC_DataReader(db.get_tables().get(0), dbFolder);
            String[] values = new String[3];
            values[0] = "23";
            values[1] = "Zhenya";
            values[2] = "Kyshko";
            
            dataReader.Write(values);

            List<Object[]> objects = dataReader.read();
            System.out.println(objects.size());
        } catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
