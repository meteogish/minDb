package minDb.Core.QueryModels.Conditions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import minDb.Core.Components.Data.IDataRow;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Column;
import minDb.Core.QueryModels.Table;

/**
 * ColumnConfition
 */
public class JoinColumnCondition extends ColumnCondition implements ICondition {
	private Column _rightColumn;
    
    public JoinColumnCondition(Table leftTable, String leftColumn, Compare compare, Table rightTable, String rightColumn) throws ValidationException {
		super(leftTable, leftColumn, compare);
		_rightColumn = new Column(rightTable, rightColumn);
	}
	
	public JoinColumnCondition(Column leftColumn, Column rightColumn, Compare compare) throws ValidationException {
		super(leftColumn, compare);

		if(rightColumn == null)
        {
            throw new ValidationException("RightColumn parameter is null.");
		}
		
		_rightColumn = rightColumn;
    }

	/**
	 * @return the _rightColumn
	 */
	public Column get_rightColumn() {
		return _rightColumn;
	}

	public Boolean apply(IDataRow row, Function<Column, Integer> columnToIndexMapper) throws ValidationException {
		Integer leftColumnIndex = columnToIndexMapper.apply(_leftColumn);
		Object leftColumnObject = row.getObject(leftColumnIndex);

		Integer rightColumnIndex = columnToIndexMapper.apply(_rightColumn);
		Object rightColumnObject = row.getObject(rightColumnIndex);

		return compareValues(leftColumnObject, rightColumnObject);
	}

	public List<Column> getConditionColumns() {
		List<Column> columns = new ArrayList<Column>(2);
		columns.add(_leftColumn);
		columns.add(_rightColumn);
		return columns;
	}   
}