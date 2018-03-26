package minDb.SqlQueryParser.Adapter.Insert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.util.ElementScanner6;

import minDb.Core.Exceptions.ValidationException;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * InsertFinder
 */
public class InsertQueryFinder implements IInsertQueryAdapter, ItemsListVisitor {
	List<Object> _values;

	public List<Object> getInsertValues(Insert insertStatement) throws ValidationException {
		ItemsList items = insertStatement.getItemsList();
		if (items == null) {
			throw new ValidationException("Insert statement is empty");
		}

		_values = new ArrayList<Object>();

		items.accept(this);

		if (_values == null) {
			throw new ValidationException("Values is empty insert statement");
		}

		return _values;
	}

	public List<String> getInsertColumns(Insert insertStatement) throws ValidationException {
		List<Column> queryColumns = insertStatement.getColumns();

		if (queryColumns == null || queryColumns.isEmpty()) {
			return new ArrayList<String>();
		} else {
			return queryColumns.stream().map(c -> c.getColumnName()).collect(Collectors.toList());
		}
	}

	@Override
	public void visit(SubSelect subSelect) {

	}

	@Override
	public void visit(ExpressionList expressionList) {
		if (expressionList == null) {
			_values = null;
		} else {
			List<Expression> expressions = expressionList.getExpressions();
			if (expressions == null || expressions.isEmpty()) {
				_values = null;
			} else {
				_values = expressions.stream().map(e -> {
					if (e instanceof LongValue) {
						return (Object) ((LongValue) e).getValue();
					} else if (e instanceof DoubleValue) {
						return (Object) ((DoubleValue) e).getValue();
					} else if (e instanceof StringValue) {
						return (Object) ((StringValue) e).getValue();
					} else if (e instanceof NullValue) {
						return null;
					} else {
						return null;
					}
				}).collect(Collectors.toList());
			}
		}
	}

	@Override
	public void visit(MultiExpressionList multiExprList) {

	}

}