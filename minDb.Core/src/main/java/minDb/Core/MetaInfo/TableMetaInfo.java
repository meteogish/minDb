package minDb.Core.MetaInfo;

import java.util.ArrayList;
import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Column;
import minDb.Core.QueryModels.Table;
import minDb.Extensions.StringExtenstions;

/**
 * TableMetaInfo
 */
public class TableMetaInfo {
    private List<ColumnMetaInfo> _columnsInfo;
    private String _tableName;

    /**
     * @return the _columnsInfo
     */
    public List<ColumnMetaInfo> get_columnsInfo() {
        return _columnsInfo;
    }

    /**
     * @return the _tableName
     */
    public String get_tableName() {
        return _tableName;
    }

    public List<Column> getColumns(Table table) throws ValidationException {
        List<Column> columns = new ArrayList<Column>(_columnsInfo.size());
        for (ColumnMetaInfo ci : _columnsInfo) {
            columns.add(new Column(table, ci.get_name()));
        }
        return columns;
    }

    public TableMetaInfo(List<ColumnMetaInfo> columnsInfo, String tableName) throws ValidationException {
        super();
        if (columnsInfo == null || columnsInfo.isEmpty()) {
            throw new ValidationException("TableMetaInfo should consists of some columns.");
        }

        if (StringExtenstions.IsNullOrEmpty(tableName)) {
            throw new ValidationException("TableName in TableMetaInfo is null/empty/whitespace.");
        }

        _columnsInfo = columnsInfo;
        _tableName = tableName;
    }

}