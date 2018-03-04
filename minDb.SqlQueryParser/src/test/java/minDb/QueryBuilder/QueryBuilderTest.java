package minDb.QueryBuilder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.SelectQuery;
import minDb.SqlQueryParser.SelectQueryBuilder;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;

/**
 * QueryBuilderTests
 */
public class QueryBuilderTest {

    private SelectQuery buildSelect(String strQuery) {
        Statement statement;
        try {
            statement = CCJSqlParserUtil.parse(strQuery);
            try {
                return new SelectQueryBuilder().buildQuery((Select) statement);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
		return null;
    }

    @Test
    public void SelectStatementTest() {
        String strQuery = "select * from Customers c";

        SelectQuery sq = buildSelect(strQuery);

        assertEquals(sq.get_from().get_name(), "Customers");
        assertEquals("c", sq.get_from().get_alias());
    }
}