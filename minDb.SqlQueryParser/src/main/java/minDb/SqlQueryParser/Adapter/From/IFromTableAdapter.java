package minDb.SqlQueryParser.Adapter.From;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Table;

/**
 * IFromTableAdapter
 */
public interface IFromTableAdapter {
    public Table getTableFromItem(net.sf.jsqlparser.statement.select.FromItem fromItem) throws ValidationException;
    public Table getTableFromSqlTable(net.sf.jsqlparser.schema.Table table) throws ValidationException;
    
}