package ConsoleEntry;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Injector;

import minDb.Core.Components.IInsertQueryExecutor;
import minDb.Core.Components.IMetaInfoRepository;
import minDb.Core.Components.IQueryParser;
import minDb.Core.Components.ISelectQueryExecutor;
import minDb.Core.Components.Data.IDataTable;
import minDb.Core.Components.Data.IRawTableWriter;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Queries.Query;
import minDb.Core.QueryModels.Queries.Query.QueryType;
import minDb.DataProvider.Logic.InsertExecutor;
import minDb.Factory.Container;
import minDb.SqlQueryParser.Adapter.Insert.IInsertQueryAdapter;

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
            String createQuery = "create table Transactions(Id INT, Amount double)";
            String insertQuery = "insert into Accounts values(45, null, 2456.23)";
            // String selectQuery = "select Id, Salary from Accounts";
            String selectQuery = "select a.Id, a.Salary, t.Amount"+
                                " from Accounts a " +
                                " join Transactions t on a.Id = t.Id";
            
            IQueryParser parser = i.getInstance(IQueryParser.class);
                        
            Query query = parser.parse(selectQuery);
            
            DatabaseMetaInfo db = repo.getDatabaseMetaInfo(Paths.get(dbFolder, "first.db").toString());
            
            if (query.get_type() == QueryType.CreateTable) {
                TableMetaInfo createTable = query.get_createTableInfo();
                db.createtable(createTable);

                IInsertQueryExecutor insertExecutor = i.getInstance(IInsertQueryExecutor.class);

                insertExecutor.execute(parser.parse("insert into Transactions values(45, 456.76)").get_insert(), db, dbFolder);
                insertExecutor.execute(parser.parse("insert into Transactions values(45, 123.33)").get_insert(), db, dbFolder);
                insertExecutor.execute(parser.parse("insert into Transactions values(11, 900.55)").get_insert(), db, dbFolder);
                
                repo.saveDatabaseMetaInfo(db, dbFolder, "first");
            }
            else if(query.get_type() == QueryType.Insert)
            {
                
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
