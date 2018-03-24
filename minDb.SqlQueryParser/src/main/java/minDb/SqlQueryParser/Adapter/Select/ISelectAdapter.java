package minDb.SqlQueryParser.Adapter.Select;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.SelectColumn;

/**
 * ISelectQuerySelectAdapter
 */
public interface ISelectAdapter {
    public List<SelectColumn> getSelectColumns(net.sf.jsqlparser.statement.select.PlainSelect plainSelect) throws ValidationException;       
}   