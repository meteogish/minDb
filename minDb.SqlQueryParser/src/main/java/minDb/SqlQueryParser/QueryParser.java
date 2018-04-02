package minDb.SqlQueryParser;

import java.util.ArrayList;
import java.util.List;

import minDb.Core.Components.IQueryParser;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Join;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.Table;
import minDb.Core.QueryModels.Conditions.ICondition;
import minDb.Core.QueryModels.Queries.Query;
import minDb.SqlQueryParser.Adapter.Create.ICreateQueryAdapter;
import minDb.SqlQueryParser.Adapter.Insert.IInsertQueryAdapter;
import minDb.SqlQueryParser.Adapter.Primitives.IPrimitivesAdapter;
import minDb.SqlQueryParser.Adapter.Select.IJoinAdapter;
import minDb.SqlQueryParser.Adapter.Select.ISelectAdapter;
import minDb.SqlQueryParser.Adapter.Select.IWhereConditionAdapter;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
/**
 * QueryParser
 */
public class QueryParser implements IQueryParser {
    private ICreateQueryAdapter _createColumnsFinder;
    private IPrimitivesAdapter _primitivesAdapter;
    private IInsertQueryAdapter _insertAdapter;
    private ISelectAdapter _selectColumnsFinder;
    private IJoinAdapter _joinsFinder;
    private IWhereConditionAdapter _whereFinder;

    public QueryParser(
        ICreateQueryAdapter createFinder,
        IPrimitivesAdapter primitivesAdapter,
        IInsertQueryAdapter insertValuesFinder,
        ISelectAdapter selectColumnsFinder,
        IJoinAdapter joinsFinder,
        IWhereConditionAdapter whereFinder) {
        _createColumnsFinder = createFinder;
        _primitivesAdapter = primitivesAdapter;
        _insertAdapter = insertValuesFinder;
        _selectColumnsFinder = selectColumnsFinder;
        _joinsFinder = joinsFinder;
        _whereFinder = whereFinder;
    }

    public Query parse(String str) throws ValidationException{
        Statement statement;
        try {
            statement = CCJSqlParserUtil.parse(str);
            if (statement instanceof Select) {
                SelectBody body = ((Select)statement).getSelectBody();
                if (!(body instanceof PlainSelect)) {
                    throw new ValidationException("Statement is not a plain select statement.");
                }

                return buildSelectQuery((PlainSelect)body);
            } else if (statement instanceof CreateTable) {
                return buildCreateTableQuery((CreateTable) statement);
            } else if (statement instanceof Insert) {
                return buildInsertQuery((Insert) statement);
            } else {
                throw new ValidationException("Unsupported statement");
            }
        } catch (JSQLParserException e) {
            throw new ValidationException("Query is not valid");
        }
    }

    private Query buildInsertQuery(Insert statement) throws ValidationException {
        return Query.buildInsertQuery(
            _primitivesAdapter.getTableFromSqlTable(statement.getTable()),
            _insertAdapter.getInsertColumns(statement),
            _insertAdapter.getInsertValues(statement));
    }

    private Query buildCreateTableQuery(CreateTable query) throws ValidationException {
        Table table = _primitivesAdapter.getTableFromSqlTable(query.getTable());

        List<ColumnMetaInfo> columns = _createColumnsFinder.getCreateTableColumns(query);

        System.out.println(query);
        return Query.buildCreateTableQuery(new TableMetaInfo(columns, table.get_name()));
    }

    private Query buildSelectQuery(PlainSelect plainSelect) throws ValidationException {
        Table from = _primitivesAdapter.getFromTable(plainSelect.getFromItem());
        List<Join> join = _joinsFinder.getJoins(plainSelect.getJoins(), from);
        
        List<Table> tables = new ArrayList<Table>(join.size() + 1);
        tables.add(from);
        join.forEach(j -> tables.add(j.get_table()));
        
        List<SelectColumn> select = _selectColumnsFinder.getSelectColumns(plainSelect, tables);
        ICondition where = _whereFinder.getWhereCondition(plainSelect, tables);
        return Query.buildSelectQuery(select, from, join, where);
    }
}