package minDb.Core.QueryModels.Conditions;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Column;

public class ValueColumnCondition<T> extends ColumnCondition implements ICondition {
    private T _value;
    
    public ValueColumnCondition(Column column, Compare compare, T value) throws ValidationException{
		super(column, compare);
        _value = value;
    }

	/**
	 * @return the _value
	 */
	public T get_value() {
		return _value;
	}
}