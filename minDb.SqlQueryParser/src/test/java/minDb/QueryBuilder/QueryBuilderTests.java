package minDb.QueryBuilder;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.SelectQuery;
import minDb.SqlQueryParser.SelectQueryBuilder;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * QueryBuilderTests
 */
public class QueryBuilderTests {
    @Test
    public void SelectStatementTest()
    {
        Statement statement;
        try {
            String strQuery = "select * from Customers c";
            statement = CCJSqlParserUtil.parse(strQuery);
            
            SelectQuery sq = new SelectQueryBuilder().buildQuery((Select)statement);
            
            assertEquals(sq.get_from().get_name(), "Customers");
            assertEquals("c", sq.get_from().get_alias());
        
        } catch (JSQLParserException e) {
			e.printStackTrace();
        } catch (ValidationException e) {
			e.printStackTrace();
		}
    }
    
}