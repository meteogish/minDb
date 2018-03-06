package minDb.SqlQueryParser;

import java.util.ArrayList;
import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Condition;
import minDb.Core.QueryModels.Join;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.SelectQuery;
import minDb.QueryBuilder.FromTableFinder;
import minDb.QueryBuilder.JoinsFinder;
import minDb.QueryBuilder.SelectColumnsFinder;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

/**
 * SelectQueryBuilder
 */
public class SelectQueryBuilder {
    private FromTableFinder _fromTableFinder = new FromTableFinder();
    private SelectColumnsFinder _selectColumnsFinder = new SelectColumnsFinder();
    private JoinsFinder _joinsFinder = new JoinsFinder();


    private List<SelectColumn> _select = new ArrayList<SelectColumn>();
    private List<Join> _join = new ArrayList<Join>();   
	private Condition _where;
	private minDb.Core.QueryModels.Table _from;
    private Integer _top;

    public SelectQuery buildQuery(Select selectStatement) throws ValidationException {
        if (!(selectStatement.getSelectBody() instanceof PlainSelect)) {
            throw new ValidationException("Statement is not a plain select statement.");
        }
        
        PlainSelect plainSelect = (PlainSelect) selectStatement.getSelectBody();
        _from = _fromTableFinder.FindFromTable(plainSelect);
        _select = _selectColumnsFinder.getSelectColumns(plainSelect);
        _join = _joinsFinder.getCoreJoinsFromParsed(plainSelect.getJoins(), _from);
        return build();
    }

    private SelectQuery build() {
        return new SelectQuery(_select, _from, _join, _where, _top);
    }

}