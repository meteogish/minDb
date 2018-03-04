package minDb.Core.MetaInfo;

/**
 * Column
 */
public class Column {
    private ColumnType _type;
    private String _name;
	/**
	 * @return the _type
	 */
	public ColumnType get_type() {
		return _type;
	}
	/**
	 * @return the _name
	 */
	public String get_name() {
		return _name;
    }
    
    public Column(ColumnType type, String name) {
        super();
        _type = type;
        _name = name;
    }    
}