package minDb.QueryBuilder;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;

/**
 * FromTableFinder
 */
public class FromTableFinder implements SelectVisitor, FromItemVisitor {
    private minDb.Core.QueryModels.Table _fromTable;

    public minDb.Core.QueryModels.Table FindFromTable(Select query) {
        query.getSelectBody().accept(this);
        return _fromTable;
    }

    public void visit(Table table) {
        if (table.getAlias() != null) {
            _fromTable = new minDb.Core.QueryModels.Table(table.getName(), table.getAlias().getName());
        } else {
            _fromTable = new minDb.Core.QueryModels.Table(table.getName());
        }
    }

    public void visit(PlainSelect plainSelect) {
        plainSelect.getFromItem().accept(this);		
    }
    
    public void visit(SubSelect subSelect) {

    }

    public void visit(SubJoin subjoin) {

    }

    public void visit(LateralSubSelect lateralSubSelect) {

    }

    public void visit(ValuesList valuesList) {

    }

    public void visit(TableFunction tableFunction) {

    }


	public void visit(SetOperationList setOpList) {
		
	}

	public void visit(WithItem withItem) {
		
	}
}