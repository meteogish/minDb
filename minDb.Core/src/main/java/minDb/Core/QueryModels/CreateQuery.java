package minDb.Core.QueryModels;

import java.util.List;

import minDb.Core.MetaInfo.Column;

/**
 * CreateQuery
 */
public class CreateQuery extends BaseQuery {
    private List<Column> _columns; 

    public CreateQuery(Table table, List<Column> columns) {
        super(table);
        _columns = columns;
    }

	/**
	 * @return the _columns
	 */
	public List<Column> get_columns() {
		return _columns;
	}    
}