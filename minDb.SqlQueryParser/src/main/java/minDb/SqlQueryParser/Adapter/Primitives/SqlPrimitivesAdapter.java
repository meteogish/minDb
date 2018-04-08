package minDb.SqlQueryParser.Adapter.Primitives;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Aggregation;
import minDb.Core.QueryModels.Column;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.Table;
import minDb.Extensions.EnumExtensions;

/**
 * SqlPrimitivesAdapter
 */
public class SqlPrimitivesAdapter implements IPrimitivesAdapter {

	@Override
	public Table getTableFromSqlTable(net.sf.jsqlparser.schema.Table table) throws ValidationException {
        if(table == null)
        {
            throw new ValidationException("FromItem is null.");            
        }

        return new minDb.Core.QueryModels.Table(table.getName(), table.getAlias() != null ? table.getAlias().getName() : null);
	}

	@Override
	public Table getFromTable(net.sf.jsqlparser.statement.select.FromItem fromItem) throws ValidationException {
        if(fromItem == null)
        {
            throw new ValidationException("FromItem is null.");            
        }

        if(!(fromItem instanceof net.sf.jsqlparser.schema.Table))
        {
            throw new ValidationException("From part of query is not valid. FromItem is not a Table type.");
        }

        return getTableFromSqlTable((net.sf.jsqlparser.schema.Table)fromItem);
	}

    @Override
    public Column getColumn(net.sf.jsqlparser.schema.Column c, List<Table> tablesUsed) throws ValidationException {
        if (c == null) {
            throw new ValidationException("Can not parse column in statement");
        }

        String fullyQualifiedName = c.getFullyQualifiedName();
        String[] columnParts = fullyQualifiedName.split("\\.");
        Table table;

        String columnName;
        if (columnParts.length > 2) {
            throw new ValidationException("Column name in where clause is not valid.");
        } else if (columnParts.length == 2) {
            String tableAlias = columnParts[0];
            table = tablesUsed.stream().filter(t -> t.get_alias().equalsIgnoreCase(tableAlias)).findFirst().get();
            if (table == null) {
                throw new ValidationException("Table alias is not valid for column in where clause");
            }
            columnName = columnParts[1];
        } else {
            table = tablesUsed.get(0);
            columnName = columnParts[0];
        }

        return new Column(table, columnName);
    }

	@Override
	public Object parseValueFromExpression(net.sf.jsqlparser.expression.Expression e) {
		if (e instanceof net.sf.jsqlparser.expression.LongValue) {
            return (Object) ((net.sf.jsqlparser.expression.LongValue) e).getValue();
        } else if (e instanceof net.sf.jsqlparser.expression.DoubleValue) {
            return (Object) ((net.sf.jsqlparser.expression.DoubleValue) e).getValue();
        } else if (e instanceof net.sf.jsqlparser.expression.StringValue) {
            return (Object) ((net.sf.jsqlparser.expression.StringValue) e).getValue();
        } else {
            return null;
        }
	}

	@Override
	public SelectColumn getAgregationColumn(net.sf.jsqlparser.expression.Function function, List<Table> tables) throws ValidationException {
        Aggregation aggregate = EnumExtensions.parse(Aggregation.class, function.getName());
		net.sf.jsqlparser.expression.Expression columnExpression = function.getParameters().getExpressions().stream().findFirst().orElse(null);
        
        if (columnExpression instanceof net.sf.jsqlparser.schema.Column) {
            throw new ValidationException("Agregation has not finished");            
            //return getColumn((net.sf.jsqlparser.schema.Column)columnExpression, tables);
        } else {
            throw new ValidationException("Agregation has not column columnExpression");
        }
	}    
}