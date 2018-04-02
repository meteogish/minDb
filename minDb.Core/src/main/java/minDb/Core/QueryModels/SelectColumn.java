package minDb.Core.QueryModels;

import minDb.Core.Exceptions.ValidationException;

/**
 * Column
 */
public class SelectColumn extends Column {
    private String _alias;

    public SelectColumn(Column column, String alias) throws ValidationException{
        super(column.get_table(), column.get_name());
        _alias = alias;
    }

    public SelectColumn(String name, Table table, String alias) throws ValidationException{
        super(table, name);
        _alias = alias;
    }
    
    /**
     * @return the _alias
     */
    public String get_alias() {
        return _alias;
    }
}