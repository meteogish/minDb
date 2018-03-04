package minDb.Core.QueryModels;

/**
 * Join
 */
public class Join {
    private Table _table;
    private Condition _condition;

    /**
     * @return the _table
     */
    public Table get_table() {
        return _table;
    }

    public Join(Table table) {
        _table = table;
    }

    public <T> void On(String leftColumn, ValueCompare compare, Table rightTable, String rightColumn) {
        _condition = new ColumnCondition(_table, leftColumn, compare, rightTable, rightColumn);
    }

	/**
	 * @return the _condition
	 */
	public Condition get_condition() {
		return _condition;
	}
}