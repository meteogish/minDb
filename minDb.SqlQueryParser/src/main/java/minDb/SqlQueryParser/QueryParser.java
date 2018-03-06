package minDb.SqlQueryParser;

import minDb.Core.Components.IQueryParser;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.BaseQuery;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;


/**
 * QueryParser
 */
public class QueryParser implements IQueryParser {
    public BaseQuery parse(String str)
    {
        Statement statement;
        try {
            statement = CCJSqlParserUtil.parse(str);
            return new SelectQueryBuilder().buildQuery((Select)statement);
            // statement.accept(new StatementVisitorAdapter(){
            //     @Override
            //     public void visit(Select select) {
            //         System.out.println("inside select");
            //         select.getSelectBody().accept(new SelectVisitorAdapter(){
            //             @Override    
            //             public void visit(PlainSelect plainSelect) {
            //                 System.out.println("inside selectBody");
            //             }
            //         });;
            //     }
            // });
            // Update update = (Update)statement;
            // CreateTable create = (CreateTable)statement;
		} catch (JSQLParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        } catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("hello pro queryparser");
        return null;
    }
    
}