package minDb.SqlQueryParser.Adapter.Select;

import minDb.Core.QueryModels.Conditions.ICondition;
import net.sf.jsqlparser.statement.select.PlainSelect;

/**
 interface IWhereConditionAdapter
 */
public interface IWhereConditionAdapter {
    ICondition getWhereCondition(PlainSelect select);    
}