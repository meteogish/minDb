package minDb.SqlQueryParser.Adapter.Select;

import java.util.ArrayList;
import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.Table;
import minDb.SqlQueryParser.Adapter.Primitives.IPrimitivesAdapter;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

/**
 * SelectColumnsFinder
 */
public class SelectColumnsFinder implements ISelectAdapter {

    private IPrimitivesAdapter _primitivesAdapter;

	public SelectColumnsFinder(IPrimitivesAdapter primitivesAdapter) {
        _primitivesAdapter = primitivesAdapter;
    }

    public List<SelectColumn> getSelectColumns(PlainSelect plainSelect, List<Table> tables) throws ValidationException {
        List<SelectColumn> select = new ArrayList<SelectColumn>();
        for (SelectItem item : plainSelect.getSelectItems()) {
            if (item instanceof AllColumns) {
                break;
            }
            add(getColumn((SelectExpressionItem) item, tables), select);
        }
        return select;
    }

    private SelectColumn getColumn(SelectExpressionItem selectExpressionItem, List<Table> tables) throws ValidationException {
        Alias aliasExpression = selectExpressionItem.getAlias();
        String alias = aliasExpression != null ? aliasExpression.getName() : null;
        
        Expression columnExpression = selectExpressionItem.getExpression();

        if(columnExpression instanceof net.sf.jsqlparser.schema.Column)
        {
           return new SelectColumn(_primitivesAdapter.getColumn((net.sf.jsqlparser.schema.Column)columnExpression, tables), alias);        
        }
        else if(columnExpression instanceof Function)
        {
            return _primitivesAdapter.getAgregationColumn((Function)columnExpression, tables);
        }
        else
        {
            throw new ValidationException("Unsupported column clause in select.");
        }
    }

    private void add(SelectColumn newColumn, List<SelectColumn> select) throws ValidationException {
        for (SelectColumn column : select) {
            if (column.get_name().equalsIgnoreCase(newColumn.get_name())) {
                throw new ValidationException("Duplicated column names in select statement.");
            }

            if (column.get_alias() != null && column.get_alias().equalsIgnoreCase(newColumn.get_alias())) {
                throw new ValidationException("Duplicated column aliases in select statement.");
            }
        }
        select.add(newColumn);
    }
}