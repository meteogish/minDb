package minDb.Core.QueryModels.Create;

import java.util.List;
import minDb.Core.MetaInfo.ColumnMetaInfo;
import minDb.Core.QueryModels.BaseQuery;
import minDb.Core.QueryModels.Table;

/**
 * CreateQuery
 */
public class CreateQuery extends BaseQuery {
    private List<ColumnMetaInfo> _columns; 

    public CreateQuery(Table table, List<ColumnMetaInfo> columns) {
        super(table);
        _columns = columns;
    }

	/**
	 * @return the _columns
	 */
	public List<ColumnMetaInfo> get_columns() {
		return _columns;
	}
}