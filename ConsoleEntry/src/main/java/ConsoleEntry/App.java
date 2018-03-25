package ConsoleEntry;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.inject.Injector;

import minDb.Core.Components.IMetaInfoRepository;
import minDb.Core.Components.IQueryParser;
import minDb.Core.Data.IDataRow;
import minDb.Core.Data.IDataTable;
import minDb.Core.Data.IRawTableReader;
import minDb.Core.Data.IRawTableWriter;
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

        try {
            String createQuery = "create table Accounts(Id INT, Name varchar(10), Salary double)";
            String insertQuery = "insert into Accounts values(45, null, 2456.23";
            IQueryParser parser = i.getInstance(IQueryParser.class);
            
            
            Query query = parser.parse(insertQuery);
            
            DatabaseMetaInfo db = repo.getDatabaseMetaInfo(Paths.get(dbFolder, "first.db").toString());
            
            if (query.get_type() == QueryType.CreateTable) {
                TableMetaInfo createTable = query.get_createTableInfo();
                List<TableMetaInfo> listTables = new ArrayList<TableMetaInfo>(1);
                listTables.add(createTable);
                DatabaseMetaInfo ndb = new DatabaseMetaInfo(listTables);
                repo.saveDatabaseMetaInfo(ndb, dbFolder, "first");
            }
            else if(query.get_type() == QueryType.Insert)
            {
                IRawTableWriter writer = i.getInstance(IRawTableWriter.class);
                writer.writeTo(db.get_tables().get(0), dbFolder, query.get_insertValues());
            }

            IRawTableReader reader = i.getInstance(IRawTableReader.class);
            IDataTable table = reader.readFrom(db.get_tables().get(0), dbFolder);

            
            // POC_DataReader dataReader = new POC_DataReader(db.get_tables().get(0), dbFolder);
            // String[] values = new String[3];
            // values[0] = "23";
            // values[1] = "Zhenya";
            // values[2] = "Kyshko";

            // dataReader.Write(values);

            // List<Object[]> objects = dataReader.read();
            // System.out.println(objects.size());
        } catch (ValidationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
