package minDb.Core.QueryModels;

import minDb.Core.Exceptions.ValidationException;

public class ValueColumnCondition<T> extends ColumnCondition {
    private T _value;
    
    public ValueColumnCondition(Table table, String column, ValueCompare compare, T value) throws ValidationException{
		super(table, column, compare);
		if(value == null)
		{
            throw new ValidationException("Value parameter is null.");
		}
        _value = value;
    }

	/**
	 * @return the _value
	 */
	public T get_value() {
		return _value;
	}
}