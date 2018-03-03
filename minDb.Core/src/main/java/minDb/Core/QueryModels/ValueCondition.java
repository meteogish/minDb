package minDb.Core.QueryModels;

public class ValueCondition<T> extends Condition {
    private T _value;
    private Compare _compare;
    
    public ValueCondition(Table table, String column, Compare compare, T value) {
        super(table, column);
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
	public Compare get_compare() {
		return _compare;
	}
}