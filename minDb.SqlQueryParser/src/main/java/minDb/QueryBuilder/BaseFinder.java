package minDb.QueryBuilder;

import minDb.Core.Exceptions.ValidationException;
import net.sf.jsqlparser.statement.select.FromItem;

/**
 * BaseFinder
 */
public abstract class BaseFinder {
    protected minDb.Core.QueryModels.Table getTableFromItem(FromItem fromItem) throws ValidationException {
        if(fromItem == null)
        {
            throw new ValidationException("FromItem is null.");            
        }

        if(!(fromItem instanceof net.sf.jsqlparser.schema.Table))
        {
            throw new ValidationException("From part of query is not valid. FromItem is not a Table type.");
        }

        net.sf.jsqlparser.schema.Table table = (net.sf.jsqlparser.schema.Table)fromItem;
        return new minDb.Core.QueryModels.Table(table.getName(), table.getAlias() != null ? table.getAlias().getName() : null);
    }    
}