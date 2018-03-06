package minDb.Core.QueryModels;

import minDb.Core.Exceptions.ValidationException;

public class ValueCondition<T> extends Condition {
	private Column _column;
	private ValueCompare _compare;
    private T _value;
    
    public ValueCondition(Table table, String column, ValueCompare compare, T value) throws ValidationException{
		_column = new Column(column);
        _compare = compare;
        _value = value;
    }

	/**
	 * @return the _value
	 */
	public T get_value() {
		return _value;
	}

	/**
	 * @return the _compare
	 */
	public ValueCompare get_compare() {
		return _compare;
	}

	/**
	 * @return the _column
	 */
	public Column get_column() {
		return _column;
	}
}