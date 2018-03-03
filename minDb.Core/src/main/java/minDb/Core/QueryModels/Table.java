package minDb.Core.QueryModels;

/**
 * Table
 */
public class Table {

    private String _name;
    private String _alias;
    private String _schema;

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