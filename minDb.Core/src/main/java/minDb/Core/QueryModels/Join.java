package minDb.Core.QueryModels;

import java.util.ArrayList;
import java.util.List;

import minDb.Core.Exceptions.ValidationException;

/**
 * Join
 */
public class Join {
    private Table _table;
    private List<ColumnCondition> _columns = new ArrayList<ColumnCondition>();

    /**
     * @return the _table
     */
    public Table get_table() {
        return _table;
    }

    public Join(Table table) {
        _table = table;
    }

    public <T> void On(String leftColumn, ValueCompare compare, Table rightTable, String rightColumn) throws ValidationException {
        _columns.add(new ColumnCondition(_table, leftColumn, compare, rightTable, rightColumn));
    }

	/**
	 * @return the _condition
	 */
	public List<ColumnCondition> get_conditions() {
		return _columns;
	}
}