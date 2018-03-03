package minDb.Core.QueryModels;

/**
 * Table
 */
public class Table {

    private String _name;
    private String _alias;
    private String _schema;

    /**
     * @return the _alias
     */
    public String get_alias() {
        return _alias;
    }

    /**
     * @return the _name
     */
    public String get_name() {
        return _name;
    }

    /**
     * @return the _schema
     */
    public String get_schema() {
        return _schema;
    }


    public Table(String name) {
        _name = name;
        _alias = null;
        _schema = null;
    }

    public Table(String name, String alias) {
        _name = name;
        _alias = alias;
        _schema = null;
    }

    public Table(String name, String alias, String schema) {
        _name = name;
        _alias = alias;
        _schema = schema;
    }    
}