package minDb.Core.QueryModels;

public class ValueCondition<T> extends Condition {
	private Column _column;
	private Compare _compare;
    private T _value;
    
    public ValueCondition(Table table, String column, Compare compare, T value){
		_column = new Column(table, column);
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

	/**
	 * @return the _column
	 */
	public Column get_column() {
		return _column;
	}
}