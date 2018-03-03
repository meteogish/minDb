package minDb.Core.QueryModels;

/**
 * ColumnConfition
 */
public class ColumnCondition extends Condition {
    private Table _rightTable;
    private String _rightColumn;
    private Compare _compare;

	/**
	 * @return the _rightTable
	 */
	public Table get_rightTable() {
		return _rightTable;
	}
	/**
	 * @return the _column
	 */
	public String get_rightColumn() {
		return _rightColumn;
    }
    
	/**
	 * @return the _compare
	 */
	public Compare get_compare() {
		return _compare;
    }
    
    public ColumnCondition(Table leftTable, String leftColumn, Compare compare, Table rightTable, String rightColumn) {
        super(leftTable, leftColumn);
        _compare = compare;
        _rightTable = rightTable;
        _rightColumn = rightColumn;
    }    
}