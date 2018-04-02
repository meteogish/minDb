package minDb.SqlQueryParser.Adapter.Select;

import java.util.ArrayList;
import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Join;
import minDb.Core.QueryModels.Table;
import minDb.Core.QueryModels.Conditions.ColumnCondition.Compare;
import minDb.Core.QueryModels.Conditions.JoinColumnCondition;
import minDb.Extensions.StringExtenstions;
import minDb.SqlQueryParser.Adapter.Primitives.IPrimitivesAdapter;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;

/**
 * JoinsFinder
 */
public class JoinsFinder implements IJoinAdapter {

    private IPrimitivesAdapter _primitivesAdapter;

	public JoinsFinder(IPrimitivesAdapter primitivesAdapter) {
        _primitivesAdapter = primitivesAdapter;
    }

    public List<Join> getJoins(List<net.sf.jsqlparser.statement.select.Join> parsedJoins, Table fromTable)
            throws ValidationException {
        List<Join> _joins = new ArrayList<Join>();
        if (parsedJoins != null && !parsedJoins.isEmpty()) {
            if(StringExtenstions.IsNullOrEmpty(fromTable.get_alias()))
            {
                throw new ValidationException("Joins are existing but table in FROM clause is without alias.");
            }

            for (net.sf.jsqlparser.statement.select.Join parsedJoin : parsedJoins) {
                Table joinTable = _primitivesAdapter.getFromTable(parsedJoin.getRightItem());
                Join join = new Join(joinTable);
                parseJoinExpression(parsedJoin.getOnExpression(), join, fromTable);
                _joins.add(join);
            }
        }

        return _joins;
    }

    private void parseJoinExpression(Expression expr, Join join, Table fromTable)
            throws ValidationException {
        if(expr == null)
        {
            return;
        }
        
        if (expr instanceof AndExpression) {
            AndExpression and = (AndExpression) expr;
            parseJoinExpression(and.getLeftExpression(), join, fromTable);
            parseJoinExpression(and.getRightExpression(), join, fromTable);
        } else if (expr instanceof EqualsTo) {
            EqualsTo onExpresison = (EqualsTo) expr;

            if (!(onExpresison.getLeftExpression() instanceof Column)) {
                throw new ValidationException("Left part of on expression in join is not column.");
            }

            minDb.Core.QueryModels.Column leftColumn = getColumnFromColumnExpression(
                    (Column) onExpresison.getLeftExpression(), join, fromTable);

            if (onExpresison.getRightExpression() instanceof Column) {
                minDb.Core.QueryModels.Column rightColumn = getColumnFromColumnExpression(
                        (Column) onExpresison.getRightExpression(), join, fromTable);

                if(leftColumn.get_table().get_alias().equals(join.get_table().get_alias()))
                {
                    join.on(new JoinColumnCondition(leftColumn, rightColumn, Compare.EQUALS));
                }
                else
                {
                    join.on(new JoinColumnCondition(rightColumn, leftColumn, Compare.EQUALS));
                }         
            } else {
                throw new ValidationException("Not supported yet");
            }
        } else {
            throw new ValidationException("Not supported yet");
        }
    }

    private minDb.Core.QueryModels.Column getColumnFromColumnExpression(Column column, Join join, Table fromTable)
            throws ValidationException {
        net.sf.jsqlparser.schema.Table table = column.getTable();
        if (table != null) {
            String tableName = table.getName();
            if(StringExtenstions.IsNullOrEmpty(tableName))
            {
                throw new ValidationException("Column without table name/alias");
            }
            
            Table columnTable = null;
            if (tableName.equalsIgnoreCase(join.get_table().get_alias())) {
                columnTable = join.get_table();
            } else if (tableName.equalsIgnoreCase(fromTable.get_alias())) {
                columnTable = fromTable;
            } else {
                throw new ValidationException("Invalid table alias in on part of join.");
            }

            return new minDb.Core.QueryModels.Column(columnTable, column.getColumnName());
        } else {
            throw new ValidationException("There is no table alias in on part of join.");
        }
    }
}