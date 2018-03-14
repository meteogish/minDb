package minDb.QueryBuilder;

import minDb.Core.Exceptions.ValidationException;
import net.sf.jsqlparser.statement.select.PlainSelect;

/**
 * FromTableFinder
 */
public class FromTableFinder extends BaseFinder {
    public minDb.Core.QueryModels.Table FindFromTable(PlainSelect query) throws ValidationException {
        return getTableFromItem(query.getFromItem());
    }
}