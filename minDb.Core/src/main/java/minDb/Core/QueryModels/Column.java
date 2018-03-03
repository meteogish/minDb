package minDb.Core.QueryModels;

/**
 * Column
 */
public class Column {
    private String _name;
    private String _alias;
    private Aggregation _aggregate;

    private Table _table;

    /**
     * @return the _name
     */
    public String get_name() {
        return _name;
    }

    /**
     * @return the _alias
     */
    public String get_alias() {
        return _alias;
    }

    public Column(Table table, String name, String alias) {
        _table = table;
        _name = name;
        _alias = alias;
    }

    /**
     * @return the _aggregate
     */
    public Aggregation get_aggregate() {
        return _aggregate;
    }

    /**
     * @return the _table
     */
    public Table get_table() {
        return _table;
    }

    public Column(Table table, Aggregation aggregate, String name, String alias) {
        _table = table;
        _name = name;
        _alias = alias;
        _aggregate = aggregate;
    }

}