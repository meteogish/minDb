package minDb.SqlQueryParser.Adapter.Primitives;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Column;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.Table;

/**
 * IPrimitivesAdapter
 */
public interface IPrimitivesAdapter {
    Table getFromTable(net.sf.jsqlparser.statement.select.FromItem fromItem) throws ValidationException;

    Table getTableFromSqlTable(net.sf.jsqlparser.schema.Table table) throws ValidationException;

    Column getColumn(net.sf.jsqlparser.schema.Column c, List<Table> tablesUsed) throws ValidationException;
    
    SelectColumn getAgregationColumn(net.sf.jsqlparser.expression.Function function, List<Table> tables) throws ValidationException;
    
    Object parseValueFromExpression(net.sf.jsqlparser.expression.Expression e);

    
    
}