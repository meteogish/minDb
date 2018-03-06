package minDb.Core.QueryModels;

import minDb.Core.Exceptions.ValidationException;

/**
 * ColumnConfition
 */
public class JoinColumnCondition extends ColumnCondition {
	private Column _rightColumn;
    
    public JoinColumnCondition(Table leftTable, String leftColumn, ValueCompare compare, Table rightTable, String rightColumn) throws ValidationException {
		super(leftTable, leftColumn, compare);
		_rightColumn = new Column(rightTable, rightColumn);
    }

	/**
	 * @return the _rightColumn
	 */
	public Column get_rightColumn() {
		return _rightColumn;
	}   
}