package minDb.Core.MetaInfo;

import minDb.Core.Exceptions.ValidationException;

/**
 * Column
 */
public class ColumnMetaInfo {
    private ColumnType _type;
    private String _name;
	/**
	 * @return the _type
	 */
	public ColumnType get_columnType() {
		return _type;
	}
	/**
	 * @return the _name
	 */
	public String get_name() {
		return _name;
    }
    
    public ColumnMetaInfo(ColumnType type, String name) throws ValidationException {
		super();
		
		if(type == null)
		{
        	throw new ValidationException("Column type is null.");
		}

		if(name == null)
		{
        	throw new ValidationException("Column name is  null/empty/whitespace.");
		}
			
        _type = type;
        _name = name;
    }    
}