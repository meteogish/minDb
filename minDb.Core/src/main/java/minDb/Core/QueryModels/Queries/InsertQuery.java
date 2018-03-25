package minDb.Core.QueryModels.Queries;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Table;

/**
 * InsertQuery
 */
public class InsertQuery {
    private Table _table;
    private List<String> _insertColumns;
    private List<Object> _insertValues;
    
    public InsertQuery(Table table, List<String> columns, List<Object> values) throws ValidationException
    {
        if(table == null)
        {
            throw new ValidationException("Table is null");
        }

        if(values == null)
        {
            throw new ValidationException("Values is null");            
        }

        if(columns == null)
        {
            throw new ValidationException("Columns is null");            
        }

        _table = table;
        _insertValues = values;
        _insertColumns = columns;
    }

	/**
	 * @return the _insertValues
	 */
	public List<Object> get_insertValues() {
		return _insertValues;
    }
    
	/**
	 * @return the _insertColumns
	 */
	public List<String> get_insertColumns() {
		return _insertColumns;
	}

	/**
	 * @return the _table
	 */
	public Table get_table() {
		return _table;
	}  
}