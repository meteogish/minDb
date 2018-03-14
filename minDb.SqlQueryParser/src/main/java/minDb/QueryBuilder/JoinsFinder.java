package minDb.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.ColumnCondition;
import minDb.Core.QueryModels.Join;
import minDb.Core.QueryModels.JoinColumnCondition;
import minDb.Core.QueryModels.Table;
import minDb.Core.QueryModels.ValueCompare;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.FromItem;

/**
 * JoinsFinder
 */
public class JoinsFinder extends BaseFinder {
    List<Join> _joins = new ArrayList<Join>();

    public List<Join> getCoreJoinsFromParsed(List<net.sf.jsqlparser.statement.select.Join> parsedJoins, Table fromTable)
            throws ValidationException {
        if (parsedJoins != null && !parsedJoins.isEmpty()) {
            for (net.sf.jsqlparser.statement.select.Join parsedJoin : parsedJoins) {
                Table joinTable = getTableFromItem(parsedJoin.getRightItem());
                Expression expr = parsedJoin.getOnExpression();
                Join join = new Join(joinTable);
                join.on(parseJoinExpression(parsedJoin.getOnExpression(), join, fromTable));
                _joins.add(join);
            }
        }

        return _joins;
    }

    private JoinColumnCondition parseJoinExpression(Expression expr, Join join, Table fromTable)
            throws ValidationException {
        if (expr instanceof AndExpression) {
            AndExpression and = (AndExpression) expr;
            join.on(parseJoinExpression(and.getLeftExpression(), join, fromTable));
            join.on(parseJoinExpression(and.getRightExpression(), join, fromTable));
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

                return new JoinColumnCondition(leftColumn, rightColumn, ValueCompare.Equals);
            } else {
                throw new ValidationException("Not supported yet");
            }
        } else {
            throw new ValidationException("Not supported yet");
        }
        return null;
    }

    private minDb.Core.QueryModels.Column getColumnFromColumnExpression(Column column, Join join, Table fromTable)
            throws ValidationException {
        net.sf.jsqlparser.schema.Table table = column.getTable();
        if (table != null) {
            String tableName = table.getName();
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