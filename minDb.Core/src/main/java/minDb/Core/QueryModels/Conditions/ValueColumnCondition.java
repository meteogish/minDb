package minDb.Core.QueryModels.Conditions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import minDb.Core.Components.Data.IDataRow;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Column;

public class ValueColumnCondition extends ColumnCondition implements ICondition {
    private Object _value;
    
    public ValueColumnCondition(Column column, Compare compare, Object value) throws ValidationException{
		super(column, compare);
        _value = value;
    }

	/**
	 * @return the _value
	 */
	public Object get_value() {
		return _value;
	}

	public Boolean apply(IDataRow row, Function<Column, Integer> columnToIndexMapper) throws ValidationException
	{
		Integer columnIndex = columnToIndexMapper.apply(_leftColumn);
		Object rowObject = row.getObject(columnIndex);
		return compareValues(rowObject, _value);
	}

	public List<Column> getConditionColumns() {
		List<Column> columns = new ArrayList<Column>(1);
		columns.add(_leftColumn);
		return columns;
	}
}