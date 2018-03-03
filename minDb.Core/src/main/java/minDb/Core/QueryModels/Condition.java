package minDb.Core.QueryModels;

/**
 * Condition
 */
public class Condition {
    private Table _table;
    private String _column;
    
    /**
     * @return the _table
     */
    public Table get_table() {
        return _table;
    }

    /**
     * @return the _column
     */
    public String get_column() {
        return _column;
    }
    
    public Condition(Table table, String column)
    {
        _table = table;
        _column = column;
    }


}