package minDb.Core.MetaInfo;

public class ColumnType {
    public enum Type {
        integer, decimal, varchar
    }

    private Type _type;
    
    private int _length;

    public ColumnType(Type type, Integer length)
    {
        _type = type;
        if(length == null)
        {
            _length = -1;
        }
        _length = length;
    }

	/**
	 * @return the _type
	 */
	public Type get_type() {
		return _type;
	}

	/**
	 * @return the _length
	 */
	public int get_length() {
		return _length;
    }
}