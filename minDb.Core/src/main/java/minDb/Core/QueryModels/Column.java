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

	public String getNameWithAlias()
	{
		if(StringExtenstions.IsNullOrEmpty(_table.get_alias()))
		{
			return get_name();
		}
		else
		{
			return get_table().get_alias() + "." + get_name();
		}
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