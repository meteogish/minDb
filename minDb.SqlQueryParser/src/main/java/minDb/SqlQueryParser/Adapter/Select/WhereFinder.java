package minDb.SqlQueryParser.Adapter.Select;

import java.util.Stack;

import minDb.Core.QueryModels.Conditions.ICondition;
import minDb.Core.QueryModels.Conditions.LogicalCondition;
import minDb.Core.QueryModels.Conditions.LogicalCondition.LogicalCompare;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.statement.select.PlainSelect;

/**
 * WhereFinder
 */
public class WhereFinder implements IWhereConditionAdapter {

    Stack<ICondition> conditions;

	@Override
	public ICondition getWhereCondition(PlainSelect select) {
        if(select.getWhere() == null)
        {
            return null;
        }
        else
        {
            conditions = new Stack<ICondition>();
            analyzeExpresion(select.getWhere());
        }
        return conditions.pop();
    }
    
    private ICondition analyzeExpresion(net.sf.jsqlparser.expression.Expression expression)
    {
        if(expression instanceof AndExpression)
        {
            AndExpression and = (AndExpression)expression;
            ICondition left = analyzeExpresion(and.getLeftExpression());
            ICondition right  = analyzeExpresion(and.getRightExpression());
            
            return new LogicalCondition(left, LogicalCompare.And, right);
        }
        else if(expression instanceof EqualsTo)
        {
            return null;
        }
        return null;
    }
}