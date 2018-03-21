package minDb.QueryBuilder.Select;

import java.util.ArrayList;
import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Aggregation;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Extensions.EnumExtensions;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.WithItem;

/**
 * SelectColumnsFinder
 */
public class SelectColumnsFinder {

    private List<SelectColumn> _select = new ArrayList<SelectColumn>();;

    public List<SelectColumn> getSelectColumns(PlainSelect plainSelect)
            throws ValidationException {
        for (SelectItem item : plainSelect.getSelectItems()) {
            if (AllColumns.class.isInstance(item)) {
                break;
            }
            add(getColumn((SelectExpressionItem) item));
        }
        return _select;
    }

    public SelectColumn getColumn(SelectExpressionItem selectExpressionItem) throws ValidationException {
        Alias aliasExpression = selectExpressionItem.getAlias();
        String alias = aliasExpression != null ? aliasExpression.getName() : null;
        
        Expression columnExpression = selectExpressionItem.getExpression();

        if(Column.class.isInstance(columnExpression))
        {
           return new SelectColumn(((Column)columnExpression).getColumnName(), alias);        
        }
        else if(Function.class.isInstance(columnExpression))
        {
            Function functionExpression = (Function)columnExpression;
            Aggregation aggregate = EnumExtensions.parse(Aggregation.class, functionExpression.getName());
            String columnName = getAgregationColumnName((Function)columnExpression);
            return new SelectColumn(columnName, alias, aggregate);
        }
        else
        {
            throw new ValidationException("Unsupported column clause in select.");
        }
    }

    public String getAgregationColumnName(Function function) throws ValidationException {
        Expression columnExpression = function.getParameters().getExpressions().stream().findFirst().orElse(null);
        if (Column.class.isInstance(columnExpression)) {
            return ((Column) columnExpression).getColumnName();
        } else {
            throw new ValidationException("Agregation is not column columnExpressionression");
        }
    }

    private void add(SelectColumn newColumn) throws ValidationException {
        for (SelectColumn column : _select) {
            if (column.get_name().equalsIgnoreCase(newColumn.get_name())) {
                throw new ValidationException("Duplicated column names in select statement.");
            }

            if (column.get_alias() != null && column.get_alias().equalsIgnoreCase(newColumn.get_alias())) {
                throw new ValidationException("Duplicated column aliases in select statement.");
            }
        }
        _select.add(newColumn);
    }

    //Region Not implemented
    public void visit(AllColumns allColumns) {
    }

    public void visit(AllTableColumns allTableColumns) {
    }

    public void visit(SetOperationList setOpList) {
    }

    public void visit(WithItem withItem) {
    }

    //Region Not impemented
}