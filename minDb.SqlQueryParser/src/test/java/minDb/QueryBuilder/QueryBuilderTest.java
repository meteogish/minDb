package minDb.QueryBuilder;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Aggregation;
import minDb.Core.QueryModels.SelectColumn;
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

    private SelectQuery buildSelect(String strQuery) throws ValidationException {
        Statement statement;
        try {
            statement = CCJSqlParserUtil.parse(strQuery);
                return new SelectQueryBuilder().buildQuery((Select) statement);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
		return null;
    }

    @Test
    public void SelectStatement_PositiveTest() throws ValidationException {
        String strQuery = "select Id as i, Another, count(ROI) as roi from Customers c";

        List<SelectColumn> expectedSelectColumns = new ArrayList<SelectColumn>(3);
        expectedSelectColumns.add(new SelectColumn("Id", "i", null));
        expectedSelectColumns.add(new SelectColumn("Another", null, null));
        expectedSelectColumns.add(new SelectColumn("ROI", "roi", Aggregation.Count));
        
        SelectQuery actualQuery = buildSelect(strQuery);

        assertEquals("Customers", actualQuery.get_from().get_name());
        assertEquals("c", actualQuery.get_from().get_alias());

        List<SelectColumn> actualSelectColumns = actualQuery.get_select();
        assertEquals(3, actualSelectColumns.size());

        for(int i = 0; i < actualSelectColumns.size(); ++i)
        {
            assertEquals(expectedSelectColumns.get(i).get_name(), actualSelectColumns.get(i).get_name());
            assertEquals(expectedSelectColumns.get(i).get_alias(), actualSelectColumns.get(i).get_alias());
            assertEquals(expectedSelectColumns.get(i).get_aggregate(), actualSelectColumns.get(i).get_aggregate());
        }
    }

    @Test(expected = ValidationException.class)
    public void SelectStatement_NegativeTest_DuplicatedAliases() throws ValidationException {
        String strQuery = "select Id as i, Another as i from Customers c";
        buildSelect(strQuery);
    }

}