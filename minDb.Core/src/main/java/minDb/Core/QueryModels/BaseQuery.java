package minDb.Core.QueryModels;

/**
 * IQuery
 */
public abstract class BaseQuery {
    private Table _table;

	/**
	 * @return the _table
	 */
	public Table get_table() {
		return _table;
    }
    
    public BaseQuery(Table table) {
        super();
        _table = table;
    }
}