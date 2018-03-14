package minDb.Core.QueryModels;

import minDb.Core.Exceptions.ValidationException;
import minDb.Extensions.StringExtenstions;

/**
 * Column
 */
public class Column {
    private String _name;
	private Table _table;
	 
	/**
	 * @return the _name
	 */
	public String get_name() {
		return _name;
	}
	
	/**
	 * @return the _table
	 */
	public Table get_table() {
		return _table;
	}
    
    public Column(String name) throws ValidationException {
		super();
		
		_name = name;
		_table = null;
	}
	
	public Column(Table table, String name) throws ValidationException {
		super();
		
		if(StringExtenstions.IsNullOrEmpty(name))
		{
			throw new ValidationException("Column name is null/empty");
		}

		_name = name;
		_table = table;
    }

}