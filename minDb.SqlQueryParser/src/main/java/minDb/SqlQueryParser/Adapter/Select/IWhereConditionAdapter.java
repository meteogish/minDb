package minDb.SqlQueryParser.Adapter.Select;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Table;
import minDb.Core.QueryModels.Conditions.ICondition;
import net.sf.jsqlparser.statement.select.PlainSelect;

/**
 interface IWhereConditionAdapter
 */
public interface IWhereConditionAdapter {
    ICondition getWhereCondition(PlainSelect select, List<Table> tablesUsed) throws ValidationException;    
}