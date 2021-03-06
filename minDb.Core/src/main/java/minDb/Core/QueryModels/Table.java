package minDb.Core.QueryModels;

import minDb.Core.Exceptions.ValidationException;
import minDb.Extensions.StringExtenstions;

/**
 * Table
 */
public class Table {

    private String _name;
    private String _alias;

    /**
     * @return the _alias
     */
    public String get_alias() {
        return _alias;
    }

    /**
     * @return the _name
     */
    public String get_name() {
        return _name;
    }


    public Table(String name) throws ValidationException {
       this(name, null);
    }

    public Table(String name, String alias) throws ValidationException {
        if(StringExtenstions.IsNullOrEmpty(name))
		{
			throw new ValidationException("Table name is null/empty");
        }
        
        _name = name;
        _alias = alias;
    }

    @Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Table))
			return false;

		Table table = (Table) other;

		return _name.equals(table._name) && _alias.equals(table._alias);
	}
}