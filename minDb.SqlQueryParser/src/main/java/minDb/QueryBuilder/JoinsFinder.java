package minDb.QueryBuilder;

import java.util.List;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Join;
import minDb.Core.QueryModels.Table;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.FromItem;

/**
 * JoinsFinder
 */
public class JoinsFinder extends BaseFinder {

    public List<Join> getCoreJoinsFromParsed(List<net.sf.jsqlparser.statement.select.Join> parsedJoins, Table fromTable) throws ValidationException
    {
        for(net.sf.jsqlparser.statement.select.Join parsedJoin : parsedJoins)
        {
            Table joinTable = getTableFromItem(parsedJoin.getRightItem());
            Expression expr = parsedJoin.getOnExpression();

            //if(expr instanceof )
        }

        return null;
    }

    
}