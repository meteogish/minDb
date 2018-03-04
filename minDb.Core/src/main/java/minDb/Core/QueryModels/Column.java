package minDb.Core.QueryModels;

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
    
    public Column(Table table, String name) {
        super();
        _name = name;
        _table = table;
    }

	/**
	 * @return the _table
	 */
	public Table get_table() {
		return _table;
	}
}