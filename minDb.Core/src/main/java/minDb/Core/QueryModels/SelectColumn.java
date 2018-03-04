package minDb.Core.QueryModels;

/**
 * Column
 */
public class SelectColumn extends Column {
    private String _alias;
    private Aggregation _aggregate;

    public SelectColumn(Table table, String name) {
        this(table, name, null, null);
    }

    public SelectColumn(Table table, String name, String alias) {
        this(table, name, alias, null);
    }

    public SelectColumn(Table table, String name, String alias, Aggregation aggregate) {
        super(table, name);
        _alias = alias;
        _aggregate = aggregate;
    }

    /**
     * @return the _aggregate
     */
    public Aggregation get_aggregate() {
        return _aggregate;
    }
    
    /**
     * @return the _alias
     */
    public String get_alias() {
        return _alias;
    }
}