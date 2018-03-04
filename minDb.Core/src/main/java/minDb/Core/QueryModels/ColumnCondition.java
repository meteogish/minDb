package minDb.Core.QueryModels;

/**
 * ColumnConfition
 */
public class ColumnCondition extends Condition {
	private Column _leftColumn;
    private ValueCompare _compare;
	private Column _rightColumn;

	/**
	 * @return the _compare
	 */
	public ValueCompare get_compare() {
		return _compare;
    }
    
    public ColumnCondition(Table leftTable, String leftColumn, ValueCompare compare, Table rightTable, String rightColumn) {
        _leftColumn = new Column(leftTable, leftColumn);
        _compare = compare;
		_rightColumn = new Column(rightTable, rightColumn);
    }

	/**
	 * @return the _leftColumn
	 */
	public Column get_leftColumn() {
		return _leftColumn;
	}

	/**
	 * @return the _rightColumn
	 */
	public Column get_rightColumn() {
		return _rightColumn;
	}   
}