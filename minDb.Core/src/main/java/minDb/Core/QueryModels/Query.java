package minDb.Core.QueryModels;

import java.util.ArrayList;
import java.util.List;

/**
 * Query
 */
public class Query {
    // private List<Column> _select = new ArrayList<Column>();
    // private List<Table> _from = new ArrayList<Table>();
    // private List<Join> _join = new ArrayList<Join>();     
    // private List<Condition> _where = new ArrayList<Condition>();
    // private Integer _top;

    private List<Column> _select = new ArrayList<Column>();
    private List<Table> _from = new ArrayList<Table>();
    private List<Join> _join = new ArrayList<Join>();     
    private List<Condition> _where = new ArrayList<Condition>();
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
	public List<Condition> get_where() {
		return _where;
	}


	/**
	 * @return the _from
	 */
	public List<Table> get_from() {
		return _from;
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
    
    public Query(List<Column> select, List<Table> from, List<Join> join, List<Condition> where, Integer top) {
        _select = select;
        _from = from;
        _join = join;
        _where = where;
        _top = top;
    }
}