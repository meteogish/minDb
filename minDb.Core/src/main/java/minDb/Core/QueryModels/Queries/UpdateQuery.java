package minDb.Core.QueryModels.Queries;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Column;
import minDb.Core.QueryModels.Table;
import minDb.Core.QueryModels.Conditions.ICondition;

/**
 * UpdateQuery
 */
public class UpdateQuery {
    private Table _table;
    private List<Column> _updateColumns;
    private List<Object> _values;
    private ICondition _where;

    public UpdateQuery(Table table, List<Column> updateColumns, List<Object> values, ICondition where) throws ValidationException {
        if(table == null)
        {
            throw new ValidationException("Table is null during build UpdateQuery object.");
        }

        if(updateColumns == null || updateColumns.isEmpty())
        {
            throw new ValidationException("There is no columns to update.");            
        }

        if(values == null || values.isEmpty())
        {
            throw new ValidationException("There is no values to update.");            
        }

        _table = table;
        _updateColumns = updateColumns;
        _values = values;
        _where = where;
    }


	/**
	 * @return the _table
	 */
	public Table get_table() {
		return _table;
	}
	/**
	 * @return the _updateColumns
	 */
	public List<Column> get_updateColumns() {
		return _updateColumns;
	}
	/**
	 * @return the _values
	 */
	public List<Object> get_values() {
		return _values;
	}
	/**
	 * @return the _where
	 */
	public ICondition get_where() {
		return _where;
	}
}