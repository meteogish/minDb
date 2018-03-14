package minDb.Core.QueryModels;

import minDb.Core.Exceptions.ValidationException;

/**
 * ColumnConfition
 */
public abstract class ColumnCondition extends Condition {
    private Column _leftColumn;
    private ValueCompare _compare;

    /**
     * @return the _compare
     */
    public ValueCompare get_compare() {
        return _compare;
    }

    /**
     * @return the _leftColumn
     */
    public Column get_leftColumn() {
        return _leftColumn;
    }

	public ColumnCondition(Table table, String column, ValueCompare compare) throws ValidationException {
        if(compare == null)
        {
            throw new ValidationException("Compare parameter is null.");
        }

        _leftColumn = new Column(table, column);
        _compare = compare;
    }
    
    public ColumnCondition(Column leftColumn, ValueCompare compare) throws ValidationException {
        if(leftColumn == null)
        {
            throw new ValidationException("Column parameter is null.");
        }

        _leftColumn = leftColumn;
        _compare = compare;
	}
}