package minDb.Core.QueryModels;

import java.util.ArrayList;
import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.TableMetaInfo;

/**
 * GodQuery
 */
public class Query {
    public enum QueryType
    {
        CreateTable,
        Insert,
        Delete,
        Select
    }

    private QueryType _type;

    private List<SelectColumn> _select;
    private Table _fromTable;
    private List<Join> _joins; 
    private Condition _where;
    
    private TableMetaInfo _createTableInfo;

    public static Query buildCreateTableQuery(TableMetaInfo tableMetaInfo)
    {
        Query q = new Query();
        q._type = QueryType.CreateTable;
        q._createTableInfo = tableMetaInfo;
        return q;
    }

    public static Query buildSelectQuery(List<SelectColumn> select, Table from, List<Join> joins, Condition where) throws ValidationException
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


        Query q = new Query();
        q._type = QueryType.Select;
        q._select = select;
        q._fromTable = from;
        q._joins = joins;
        q._where = where;
        return q;
    }

	/**
	 * @return the _createTableInfo
	 */
	public TableMetaInfo get_createTableInfo() {
		return _createTableInfo;
	}

	/**
	 * @return the _select
	 */
	public List<SelectColumn> get_select() {
		return _select;
	}

	/**
	 * @return the _fromTable
	 */
	public Table get_fromTable() {
		return _fromTable;
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

	/**
	 * @return the _type
	 */
	public QueryType get_type() {
		return _type;
	}
}