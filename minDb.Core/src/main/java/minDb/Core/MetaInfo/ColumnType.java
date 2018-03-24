package minDb.Core.MetaInfo;

public class ColumnType {
    public enum Type {
        integer, decimal, varchar
    }

    private Type _type;    
    private Integer _length;

    public ColumnType(Type type, Integer length)
    {
        _type = type;
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
	public Integer get_length() {
		return _length;
    }
}