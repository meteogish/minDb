package minDb.SqlQueryParser.Adapter.Select;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Join;
import minDb.Core.QueryModels.Table;

/**
 * ISelectQueryJoinAdapter
 */
public interface IJoinAdapter {
    public List<Join> getJoins(List<net.sf.jsqlparser.statement.select.Join> parsedJoins, Table fromTable) throws ValidationException;
}