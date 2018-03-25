package ConsoleEntry;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.inject.Injector;

import minDb.Core.Components.IMetaInfoRepository;
import minDb.Core.Components.IQueryParser;
import minDb.Core.Components.ISelectQueryExecutor;
import minDb.Core.Data.IDataRow;
import minDb.Core.Data.IDataTable;
import minDb.Core.Data.IRawTableReader;
import minDb.Core.Data.IRawTableWriter;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Queries.Query;
import minDb.Core.QueryModels.Queries.Query.QueryType;
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
            String selectQuery = "select Id, Salary from Accounts";
            IQueryParser parser = i.getInstance(IQueryParser.class);
            
            
            Query query = parser.parse(selectQuery);
            
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
                writer.writeTo(db.get_tables().get(0), dbFolder, query.get_insert().get_insertValues());
            }
            else if(query.get_type() == QueryType.Select)
            {
                ISelectQueryExecutor exec = i.getInstance(ISelectQueryExecutor.class);
                IDataTable table = exec.execute(query.get_select(), db, dbFolder);
                table.print();
            }
            
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}
