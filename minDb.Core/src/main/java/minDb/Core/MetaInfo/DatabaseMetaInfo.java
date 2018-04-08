package minDb.Core.MetaInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import minDb.Core.Exceptions.ValidationException;
import minDb.Extensions.StringExtenstions;

/**
 * DatabaseMetaInfo
 */
public class DatabaseMetaInfo {
    private List<TableMetaInfo> _tables;

    /**
     * @return the _tables
     */
    public List<TableMetaInfo> get_tables() {
        return _tables;
    }

    public void createtable(TableMetaInfo table) {
        _tables.add(table);
    }

    public void dropTable(TableMetaInfo table) {
        _tables.remove(table);
    }

    public DatabaseMetaInfo(List<TableMetaInfo> tables) {
        super();
        _tables = tables;
    }

    public DatabaseMetaInfo() {
        _tables = new ArrayList<TableMetaInfo>();
    }

    public TableMetaInfo getTableMetaInfo(String tableName) throws ValidationException {
        if(StringExtenstions.IsNullOrEmpty(tableName))
        {
            throw new ValidationException("tableName parameter is null/empty/whitespace");
        }

        for (TableMetaInfo t : _tables) {
            if (t.get_tableName().equalsIgnoreCase(tableName)) {
                return t;
            }
        }
        throw new ValidationException("Can not find table " + tableName + " in the db");
    }
}