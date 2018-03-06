package minDb.Core.QueryModels;

import minDb.Core.Exceptions.ValidationException;

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
    
    public ColumnCondition(Table leftTable, String leftColumn, ValueCompare compare, Table rightTable, String rightColumn) throws ValidationException {
        _leftColumn = new Column(leftColumn);
        _compare = compare;
		_rightColumn = new Column(rightColumn);
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