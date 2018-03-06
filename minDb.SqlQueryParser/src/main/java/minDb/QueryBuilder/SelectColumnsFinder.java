package minDb.QueryBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Aggregation;
import minDb.Core.QueryModels.Column;
import minDb.Core.QueryModels.ColumnCondition;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.Table;
import minDb.Extensions.StringExtenstions;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.WithItem;


/**
 * SelectColumnsFinder
 */
public class SelectColumnsFinder {

    private minDb.Core.QueryModels.Table _fromTable;
    private List<Column> _select;

    public List<Column> getSelectColumns(Select query, minDb.Core.QueryModels.Table fromTable) throws ValidationException {
        _fromTable = fromTable;
        _select = new ArrayList<Column>();
        PlainSelect plainSelect = (PlainSelect) query.getSelectBody();

        if (plainSelect.getSelectItems() != null) {
            for (SelectItem item : plainSelect.getSelectItems()) {
                getColumn((SelectExpressionItem)item);
            }
        }

        return _select;
    }

    public void getColumn(SelectExpressionItem selectExpressionItem) {
        Alias alias = selectExpressionItem.getAlias();
        Expression exp = selectExpressionItem.getExpression();
        SelectColumn current;
        if(net.sf.jsqlparser.schema.Column.class.isInstance(exp))
        {
            _select.add(getColumn((net.sf.jsqlparser.schema.Column)exp, selectExpressionItem.getAlias()));            
        }
        else if(Function.class.isInstance(exp))
        {
            _select.add(getAgregationColumn((Function)exp));
        }
    }

    public Column getAgregationColumn(Function function) {
        String name = function.getName();
        Aggregation agregation = Arrays.stream(Aggregation.values()).filter(e -> e.name().equalsIgnoreCase(name))
                .findAny().orElse(null);

        Expression exp = function.getParameters().getExpressions().stream().findFirst().orElse(null);
        
        return null;
    }

    private SelectColumn getColumn(net.sf.jsqlparser.schema.Column columnExpression, Alias alias) {
        if(alias != null)
        {
            return new SelectColumn(columnExpression.getColumnName(), alias.getName());
        }
        return new SelectColumn(columnExpression.getColumnName());
    }

	public void visit(AllColumns allColumns) {
	}

	public void visit(AllTableColumns allTableColumns) {
	}

	public void visit(SetOperationList setOpList) {
	}

	public void visit(WithItem withItem) {		
	}
}