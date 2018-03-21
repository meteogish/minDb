package minDb.SqlQueryParser;

import java.util.List;

import minDb.Core.Components.IQueryParser;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Condition;
import minDb.Core.QueryModels.Join;
import minDb.Core.QueryModels.Query;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.Table;
import minDb.QueryBuilder.CreateTable.CreateQueryFinder;
import minDb.QueryBuilder.Select.FromTableFinder;
import minDb.QueryBuilder.Select.JoinsFinder;
import minDb.QueryBuilder.Select.SelectColumnsFinder;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;


/**
 * QueryParser
 */
public class QueryParser implements IQueryParser {
    private CreateQueryFinder _createFinder;    
    private FromTableFinder _fromTableFinder = new FromTableFinder();
    private SelectColumnsFinder _selectColumnsFinder = new SelectColumnsFinder();
    private JoinsFinder _joinsFinder = new JoinsFinder();

    public QueryParser(CreateQueryFinder createFinder, FromTableFinder fromFinder, SelectColumnsFinder selectFinder, JoinsFinder joinsFinder) {
        _createFinder = createFinder;
        _fromTableFinder = fromFinder;
        _selectColumnsFinder = selectFinder;
        _joinsFinder = joinsFinder;
    }

    public Query parse(String str)
    {
        Statement statement;
        try {
            statement = CCJSqlParserUtil.parse(str);
            if(statement instanceof Select)
            {
                return buildSelectQuery((Select)statement);
            }
            else if(statement instanceof CreateTable)
            {
                return buildCreateTableQuery((CreateTable)statement);
            }
            else
            {
                throw new ValidationException("Unsupported statement");
            }
		} catch (JSQLParserException e) {
			e.printStackTrace();
        } catch (ValidationException e) {
			e.printStackTrace();
        }
        return null;
    }


    private Query buildCreateTableQuery(CreateTable query) throws ValidationException
    {
        Table table = _createFinder.getTableFromSqlTable(query.getTable());

        List<ColumnMetaInfo> columns = _createFinder.getCreateTableColumns(query);

        System.out.println(query);
        return Query.buildCreateTableQuery(new TableMetaInfo(columns, table.get_name()));
    } 
    
    private Query buildSelectQuery(Select selectStatement) throws ValidationException {
        if (!(selectStatement.getSelectBody() instanceof PlainSelect)) {
            throw new ValidationException("Statement is not a plain select statement.");
        }
        
        PlainSelect plainSelect = (PlainSelect) selectStatement.getSelectBody();
        minDb.Core.QueryModels.Table from = _fromTableFinder.FindFromTable(plainSelect);
        List<SelectColumn> select = _selectColumnsFinder.getSelectColumns(plainSelect);
        List<Join> join = _joinsFinder.getCoreJoinsFromParsed(plainSelect.getJoins(), from);
        return Query.buildSelectQuery(select, from, join, new Condition());
    }
}