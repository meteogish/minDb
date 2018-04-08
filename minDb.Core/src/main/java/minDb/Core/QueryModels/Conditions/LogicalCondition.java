package minDb.Core.QueryModels.Conditions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import minDb.Core.Components.Data.IDataRow;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Column;

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

	public Boolean apply(IDataRow row, Function<Column, Integer> columnToIndexMapper) throws ValidationException {
		if(_compare == LogicalCompare.And)
		{
			return _leftCondition.apply(row, columnToIndexMapper) && _righCondition.apply(row, columnToIndexMapper);
		}
		else if(_compare == LogicalCompare.Or)
		{
			return _leftCondition.apply(row, columnToIndexMapper) || _righCondition.apply(row, columnToIndexMapper);			
		}
		else {
			throw new ValidationException("Unknown logical condition");
		}
	}

	public List<Column> getConditionColumns() {
		List<Column> columns = new ArrayList<Column>(2);
		columns.addAll(_leftCondition.getConditionColumns());
		columns.addAll(_righCondition.getConditionColumns());
		return columns;
	}
}