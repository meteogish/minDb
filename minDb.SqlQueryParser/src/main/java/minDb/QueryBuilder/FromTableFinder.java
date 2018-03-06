package minDb.QueryBuilder;

import minDb.Core.Exceptions.ValidationException;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;

/**
 * FromTableFinder
 */
public class FromTableFinder {
    private minDb.Core.QueryModels.Table _fromTable;

    public minDb.Core.QueryModels.Table FindFromTable(PlainSelect query) throws ValidationException {
        return getTableFromItem(query.getFromItem());
    }

    public minDb.Core.QueryModels.Table getTableFromItem(FromItem fromItem) throws ValidationException {
        if(fromItem == null)
        {
            throw new ValidationException("FromItem is null.");            
        }

        if(!Table.class.isInstance(fromItem))
        {
            throw new ValidationException("From part of query is not valid. FromItem is not a Table type.");
        }

        Table table = (Table)fromItem;
        return new minDb.Core.QueryModels.Table(table.getName(), table.getAlias() != null ? table.getAlias().getName() : null);
    }
}