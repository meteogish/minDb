package minDb.Core.QueryModels.Queries;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Column;
import minDb.Core.QueryModels.Join;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.Table;
import minDb.Core.QueryModels.Conditions.ICondition;

/**
 * GodQuery
 */
public class Query {
    public enum QueryType {
        CreateTable, Insert, Update, Select, DropTable
    }

    private Table _dropTable;

    private QueryType _type;

    private SelectQuery _select;

    private TableMetaInfo _createTableInfo;

    private InsertQuery _insert;

    private UpdateQuery _update;

    public static Query buildCreateTableQuery(TableMetaInfo tableMetaInfo) {
        Query q = new Query();
        q._type = QueryType.CreateTable;
        q._createTableInfo = tableMetaInfo;
        return q;
    }

    public static Query buildSelectQuery(List<SelectColumn> select, Table from, List<Join> joins, ICondition where)
            throws ValidationException {
        Query q = new Query();
        q._type = QueryType.Select;
        q._select = new SelectQuery(select, from, joins, where);
        return q;
    }

    public static Query buildInsertQuery(Table table, List<String> columns, List<Object> values)
            throws ValidationException {
        Query q = new Query();
        q._type = QueryType.Insert;
        q._insert = new InsertQuery(table, columns, values);
        return q;
    }

    public static Query buildDropTableQuery(Table dropTable) {
        Query q = new Query();
        q._type = QueryType.DropTable;
        q._dropTable = dropTable;
        return q;
    }

    public static Query buildUpdateQuery(Table table, List<Column> updateColumns, List<Object> values, ICondition where) throws ValidationException
    {
        Query q = new Query();
        q._type = QueryType.Update;
        q._update = new UpdateQuery(table, updateColumns, values, where);
        return q;
    }

    /**
     * @return the _createTableInfo
     */
    public TableMetaInfo get_createTableInfo() {
        return _createTableInfo;
    }

    /**
     * @return the _type
     */
    public QueryType get_type() {
        return _type;
    }

    /**
     * @return the _select
     */
    public SelectQuery get_select() {
        return _select;
    }

    /**
     * @return the _insert
     */
    public InsertQuery get_insert() {
        return _insert;
    }

    /**
     * @return the _dropTable
     */
    public Table get_dropTable() {
        return _dropTable;
    }

	/**
	 * @return the _update
	 */
	public UpdateQuery get_update() {
		return _update;
	}
}