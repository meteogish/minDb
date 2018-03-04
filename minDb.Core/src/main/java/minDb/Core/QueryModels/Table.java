package minDb.Core.QueryModels;

/**
 * Table
 */
public class Table {

    private String _name;
    private String _alias;

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


    public Table(String name) {
       this(name, null);
    }

    public Table(String name, String alias) {
        _name = name;
        _alias = alias;
    } 
}