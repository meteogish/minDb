package minDb.Core.QueryModels;

/**
 * LogicalCondition
 */
public class LogicalCondition extends Condition {
    private Condition _leftCondition;
    private Condition _righCondition;
    private LogicalCompare _compare;
    
    public LogicalCondition(Condition left, LogicalCompare compare, Condition right) {
        super();
        _leftCondition = left;
        _compare = compare;
        _righCondition = right;
    }

	/**
	 * @return the _leftCondition
	 */
	public Condition get_leftCondition() {
		return _leftCondition;
	}

	/**
	 * @return the _righCondition
	 */
	public Condition get_righCondition() {
		return _righCondition;
	}

	/**
	 * @return the _compare
	 */
	public LogicalCompare get_compare() {
		return _compare;
    }
}