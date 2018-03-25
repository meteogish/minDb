package minDb.Core.QueryModels.Queries;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Condition;
import minDb.Core.QueryModels.Join;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.Table;

/**
 * SelectQuery
 */
public class SelectQuery {
    private List<SelectColumn> _select;
    private Table _table;
    private List<Join> _joins; 
    private Condition _where;

    public SelectQuery(List<SelectColumn> select, Table from, List<Join> joins, Condition where) throws ValidationException
    {
        if(from == null)
        {
            throw new ValidationException("From is null during build SelectQuery object.");
        }
        
        if(joins == null)
        {
            throw new ValidationException("Joins is null during build SelectQuery object.");
        }

        if(where == null)
        {
            throw new ValidationException("Where is null during build SelectQuery object.");
        }

        if(select == null)
        {
            throw new ValidationException("Select is null during build SelectQuery object.");
        }

        _select = select;
        _table = from;
        _joins = joins;
        _where = where;
    }

	/**
	 * @return the _select
	 */
	public List<SelectColumn> get_select() {
		return _select;
	}
	/**
	 * @return the _table
	 */
	public Table get_table() {
		return _table;
	}
	/**
	 * @return the _joins
	 */
	public List<Join> get_joins() {
		return _joins;
	}
	/**
	 * @return the _where
	 */
	public Condition get_where() {
		return _where;
	}

    
    
}