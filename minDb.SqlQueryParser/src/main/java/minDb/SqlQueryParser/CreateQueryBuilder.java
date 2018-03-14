package minDb.SqlQueryParser;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnMetaInfo;
import minDb.Core.QueryModels.Table;
import minDb.Core.QueryModels.Create.CreateQuery;
import minDb.QueryBuilder.CreateQueryFinder;
import net.sf.jsqlparser.statement.create.table.CreateTable;

/**
 * CreateQueryBuilder
 */
public class CreateQueryBuilder {
    CreateQueryFinder _finder = new CreateQueryFinder();

    public CreateQuery buildQuery(CreateTable query) throws ValidationException
    {
        Table table = _finder.getTableFromSqlTable(query.getTable());

        List<ColumnMetaInfo> columns = _finder.getCreateTableColumns(query);

        System.out.println(query);
        return new CreateQuery(table, columns);
    } 
    
}