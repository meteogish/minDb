package minDb.SqlQueryParser.Adapter.Insert;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * InsertFinder
 */
public class InsertQueryFinder implements IInsertQueryAdapter, ItemsListVisitor {
    public List<Object> getInsertValues(Insert insertStatement)
    {
        List<Object> values = new ArrayList<Object>();
        ItemsList items = insertStatement.getItemsList();
        items.accept(this);
        return null;
    }

	@Override
	public void visit(SubSelect subSelect) {
		
	}

	@Override
	public void visit(ExpressionList expressionList) {
		
	}

	@Override
	public void visit(MultiExpressionList multiExprList) {
		
	}    
}