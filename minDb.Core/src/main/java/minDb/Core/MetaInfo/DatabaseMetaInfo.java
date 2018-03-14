package minDb.Core.MetaInfo;

import java.util.List;

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
    
    public DatabaseMetaInfo(List<TableMetaInfo> tables) {
        super();
        _tables = tables;
    }
    
}