package minDb.QueryBuilder;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Table;
import minDb.QueryBuilder.CreateQueryFinder;
import net.sf.jsqlparser.statement.create.table.CreateTable;

/**
 * CreateQueryBuilder
 */
public class CreateQueryBuilder {
    CreateQueryFinder _finder = new CreateQueryFinder();

    public TableMetaInfo buildQuery(CreateTable query) throws ValidationException
    {
        Table table = _finder.getTableFromSqlTable(query.getTable());

        List<ColumnMetaInfo> columns = _finder.getCreateTableColumns(query);

        System.out.println(query);
        return new TableMetaInfo(columns, table.get_name());
    } 
    
}