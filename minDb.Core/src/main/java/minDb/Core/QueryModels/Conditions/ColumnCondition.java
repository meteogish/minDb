package minDb.Core.QueryModels.Conditions;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Column;
import minDb.Core.QueryModels.Table;

/**
 * ColumnConfition
 */
public abstract class ColumnCondition {
    public enum Compare
    {
        Equals,
        NotEquals,
        GreaterThan,
        LessThan
    }

    private Column _leftColumn;
    private Compare _compare;

    /**
     * @return the _compare
     */
    public Compare get_compare() {
        return _compare;
    }

    /**
     * @return the _leftColumn
     */
    public Column get_leftColumn() {
        return _leftColumn;
    }

	public ColumnCondition(Table table, String column, Compare compare) throws ValidationException {
        if(compare == null)
        {
            throw new ValidationException("Compare parameter is null.");
        }

        _leftColumn = new Column(table, column);
        _compare = compare;
    }
    
    public ColumnCondition(Column leftColumn, Compare compare) throws ValidationException {
        if(leftColumn == null)
        {
            throw new ValidationException("Column parameter is null.");
        }

        _leftColumn = leftColumn;
        _compare = compare;
	}
}