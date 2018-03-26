package minDb.Core.QueryModels.Conditions;

/**
 * LogicalCondition
 */
public class LogicalCondition implements ICondition {
	public enum LogicalCompare {
		And,
		Or    
	}

    private ICondition _leftCondition;
    private ICondition _righCondition;
    private LogicalCompare _compare;
    
    public LogicalCondition(ICondition left, LogicalCompare compare, ICondition right) {
        _leftCondition = left;
        _compare = compare;
        _righCondition = right;
    }

	/**
	 * @return the _leftCondition
	 */
	public ICondition get_leftCondition() {
		return _leftCondition;
	}

	/**
	 * @return the _righCondition
	 */
	public ICondition get_righCondition() {
		return _righCondition;
	}

	/**
	 * @return the _compare
	 */
	public LogicalCompare get_compare() {
		return _compare;
    }
}