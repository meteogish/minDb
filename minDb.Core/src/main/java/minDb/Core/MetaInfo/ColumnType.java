package minDb.Core.MetaInfo;

public class ColumnType {
    public enum Type {
        integer, decimal, varchar
    }

    private Type _type;
    
    private int _length;

    public ColumnType(Type type, int length)
    {
        _type = type;
        _length = length;
    }

    public ColumnType(Type type)
    {
        _type = type;
        _length = -1;
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