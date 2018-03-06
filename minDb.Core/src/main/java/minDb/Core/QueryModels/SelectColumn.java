package minDb.Core.QueryModels;

import minDb.Core.Exceptions.ValidationException;

/**
 * Column
 */
public class SelectColumn extends Column {
    private String _alias;
    private Aggregation _aggregate;

    public SelectColumn(String name) throws ValidationException {
        this(name, null, null);
    }

    public SelectColumn(String name, String alias) throws ValidationException{
        this(name, alias, null);
    }

    public SelectColumn(String name, String alias, Aggregation aggregate) throws ValidationException {
        super(name);
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