package minDb.Core.QueryModels;

import java.util.ArrayList;
import java.util.List;

/**
 * Query
 */
public class SelectQuery extends BaseQuery {
    private List<Column> _select = new ArrayList<Column>();
    private List<Join> _join = new ArrayList<Join>();   
	private Condition _where;
	
    private Integer _top;

	/**
	 * @return the _select
	 */
	public List<Column> get_select() {
		return _select;
	}


	/**
	 * @return the _where
	 */
	public Condition get_where() {
		return _where;
	}


	/**
	 * @return the _from
	 */
	public Table get_from() {
		return get_table();
	}

	/**
	 * @return the _join
	 */
	public List<Join> get_join() {
		return _join;
	}

	/**
	 * @return the _top
	 */
	public Integer get_top() {
		return _top;
	}
    
    public SelectQuery(List<Column> select, Table from, List<Join> join, Condition where, Integer top) {
		super(from);
        _select = select;
        _join = join;
        _where = where;
        _top = top;
    }
}