package minDb.Core.MetaInfo;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
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
    
    public TableMetaInfo(List<ColumnMetaInfo> columnsInfo, String tableName) throws ValidationException {
        super();
        if(columnsInfo == null || columnsInfo.isEmpty())
        {
            throw new ValidationException("TableMetaInfo should consists of some columns.");
        }

        if(StringExtenstions.IsNullOrEmpty(tableName))
        {
            throw new ValidationException("TableName in TableMetaInfo is null/empty/whitespace.");            
        }
        
        _columnsInfo = columnsInfo;
        _tableName = tableName;
    }
    
}