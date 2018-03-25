package minDb.SqlQueryParser;

import java.util.List;
import java.util.stream.Collectors;

import minDb.Core.Components.IQueryParser;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Condition;
import minDb.Core.QueryModels.Join;
import minDb.Core.QueryModels.Query;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.Table;
import minDb.SqlQueryParser.Adapter.Create.ICreateQueryAdapter;
import minDb.SqlQueryParser.Adapter.From.IFromTableAdapter;
import minDb.SqlQueryParser.Adapter.Insert.IInsertQueryAdapter;
import minDb.SqlQueryParser.Adapter.Select.IJoinAdapter;
import minDb.SqlQueryParser.Adapter.Select.ISelectAdapter;
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
    private IFromTableAdapter _fromtableFinder;
    private IInsertQueryAdapter _insertAdapter;
    private ISelectAdapter _selectColumnsFinder;
    private IJoinAdapter _joinsFinder;

    public QueryParser(
        ICreateQueryAdapter createFinder,
        IFromTableAdapter fromtableFinder,
        IInsertQueryAdapter insertValuesFinder,
        ISelectAdapter selectColumnsFinder,
        IJoinAdapter joinsFinder) {
        _createColumnsFinder = createFinder;
        _fromtableFinder = fromtableFinder;
        _insertAdapter = insertValuesFinder;
        _selectColumnsFinder = selectColumnsFinder;
        _joinsFinder = joinsFinder;
    }

    public Query parse(String str) {
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
            System.out.println(e.getMessage());
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Query buildInsertQuery(Insert statement) throws ValidationException {
        return Query.buildInsertQuery(
            _fromtableFinder.getTableFromSqlTable(statement.getTable()),
            _insertAdapter.getInsertColumns(statement),
            _insertAdapter.getInsertValues(statement));
    }

    private Query buildCreateTableQuery(CreateTable query) throws ValidationException {
        Table table = _fromtableFinder.getTableFromSqlTable(query.getTable());

        List<ColumnMetaInfo> columns = _createColumnsFinder.getCreateTableColumns(query);

        System.out.println(query);
        return Query.buildCreateTableQuery(new TableMetaInfo(columns, table.get_name()));
    }

    private Query buildSelectQuery(PlainSelect plainSelect) throws ValidationException {
        Table from = _fromtableFinder.getTableFromItem(plainSelect.getFromItem());
        List<SelectColumn> select = _selectColumnsFinder.getSelectColumns(plainSelect);
        List<Join> join = _joinsFinder.getJoins(plainSelect.getJoins(), from);
        return Query.buildSelectQuery(select, from, join, new Condition());
    }
}