package minDb.Core.QueryModels;

import minDb.Core.Exceptions.ValidationException;
import minDb.Extensions.StringExtenstions;

/**
 * Column
 */
public class Column {
    private String _name;

	/**
	 * @return the _name
	 */
	public String get_name() {
		return _name;
    }
    
    public Column(String name) throws ValidationException {
		super();
		
		if(StringExtenstions.IsNullOrEmpty(name))
		{
			throw new ValidationException("Column name is null/empty");
		}

        _name = name;
    }
}