package minDb.SqlQueryParser.Adapter.Select;

import java.util.List;
import java.util.Stack;

import javax.lang.model.util.ElementScanner6;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Column;
import minDb.Core.QueryModels.Table;
import minDb.Core.QueryModels.Conditions.ICondition;
import minDb.Core.QueryModels.Conditions.JoinColumnCondition;
import minDb.Core.QueryModels.Conditions.LogicalCondition;
import minDb.Core.QueryModels.Conditions.ValueColumnCondition;
import minDb.Core.QueryModels.Conditions.ColumnCondition.Compare;
import minDb.Core.QueryModels.Conditions.LogicalCondition.LogicalCompare;
import minDb.Extensions.EnumExtensions;
import minDb.Extensions.StringExtenstions;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.statement.select.PlainSelect;

/**
 * WhereFinder
 */
public class WhereFinder implements IWhereConditionAdapter {
    @Override
    public ICondition getWhereCondition(PlainSelect select, List<Table> tablesUsed) throws ValidationException {
        if (select.getWhere() == null) {
            return null;
        } else {
            if (tablesUsed == null || tablesUsed.isEmpty()) {
                throw new ValidationException("There is no used tables to perform where clause");
            }

            return analyzeExpresion(select.getWhere(), tablesUsed);
        }
    }

    private ICondition analyzeExpresion(net.sf.jsqlparser.expression.Expression expression, List<Table> tablesUsed)
            throws ValidationException {
        if (expression instanceof AndExpression) {
            AndExpression and = (AndExpression) expression;
            ICondition left = analyzeExpresion(and.getLeftExpression(), tablesUsed);
            ICondition right = analyzeExpresion(and.getRightExpression(), tablesUsed);

            return new LogicalCondition(left, LogicalCompare.And, right);

        } else if (expression instanceof OrExpression) {
            OrExpression and = (OrExpression) expression;
            ICondition left = analyzeExpresion(and.getLeftExpression(), tablesUsed);
            ICondition right = analyzeExpresion(and.getRightExpression(), tablesUsed);
            return new LogicalCondition(left, LogicalCompare.Or, right);
        } else if (expression instanceof EqualsTo) {
            EqualsTo equalsExpression = (EqualsTo) expression;
            return getCondition(equalsExpression.getLeftExpression(), equalsExpression.getRightExpression(), tablesUsed, Compare.EQUALS);
        }
        else if(expression instanceof NotEqualsTo)
        {
            NotEqualsTo notEqualsExpression = (NotEqualsTo) expression;
            return getCondition(notEqualsExpression.getLeftExpression(), notEqualsExpression.getRightExpression(), tablesUsed, Compare.NOT_EQUALS);
        } 
        else if(expression instanceof GreaterThan)
        {
            GreaterThan expr = (GreaterThan) expression;
            return getCondition(expr.getLeftExpression(), expr.getRightExpression(), tablesUsed, Compare.GREATER);   
        }
        else if(expression instanceof MinorThan)
        {
            MinorThan expr = (MinorThan) expression;
            return getCondition(expr.getLeftExpression(), expr.getRightExpression(), tablesUsed, Compare.LESS);   
        }
        else if (expression instanceof IsNullExpression) {
            IsNullExpression isNullExpression = (IsNullExpression) expression;
            Compare compare;
            if (isNullExpression.isNot()) {
                compare = Compare.NOT_EQUALS;
            } else {
                compare = Compare.EQUALS;
            }

            if (isNullExpression.getLeftExpression() instanceof net.sf.jsqlparser.schema.Column) {
                return new ValueColumnCondition<Object>(
                        getColumn((net.sf.jsqlparser.schema.Column) isNullExpression.getLeftExpression(), tablesUsed),
                        compare, null);
            } else {
                throw new ValidationException("Not valid column in WHERE ISNULL expression");
            }
        }
        return null;
    }

    private ICondition getCondition(Expression left, Expression right, List<Table> tablesUsed, Compare compare) throws ValidationException
    {
        boolean leftIsColumn = left instanceof net.sf.jsqlparser.schema.Column;
            boolean rightIsColumn = right instanceof net.sf.jsqlparser.schema.Column;

            Column leftColumn;
            Object value;

            if (leftIsColumn && rightIsColumn) {
                leftColumn = getColumn((net.sf.jsqlparser.schema.Column) left,
                        tablesUsed);
                Column rightColumn = getColumn((net.sf.jsqlparser.schema.Column) right,
                        tablesUsed);
                return new JoinColumnCondition(leftColumn, rightColumn, compare);
            } else if (leftIsColumn && !rightIsColumn) {
                leftColumn = getColumn((net.sf.jsqlparser.schema.Column) left,
                        tablesUsed);
                value = parseValue(right);
                return new ValueColumnCondition<Object>(leftColumn, compare, value);
            } else if (!leftIsColumn && rightIsColumn) {
                leftColumn = getColumn((net.sf.jsqlparser.schema.Column) right,
                        tablesUsed);
                value = parseValue(left);
                return new ValueColumnCondition<Object>(leftColumn, compare, value);
            }
            else
            {
                throw new ValidationException("Unsupported statement");
            }
    }
    private Object parseValue(Expression e) {
        if (e instanceof LongValue) {
            return (Object) ((LongValue) e).getValue();
        } else if (e instanceof DoubleValue) {
            return (Object) ((DoubleValue) e).getValue();
        } else if (e instanceof StringValue) {
            return (Object) ((StringValue) e).getValue();
        } else {
            return null;
        }
    }

    private Compare getCompare(String stringExpression) throws ValidationException {
        if(StringExtenstions.IsNullOrEmpty(stringExpression))
        {
            throw new ValidationException("Invalid compare symbol in where clause");
        }
        
        return EnumExtensions.parse(Compare.class, stringExpression);
    }

    private Column getColumn(net.sf.jsqlparser.schema.Column c, List<Table> tablesUsed) throws ValidationException {
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
}