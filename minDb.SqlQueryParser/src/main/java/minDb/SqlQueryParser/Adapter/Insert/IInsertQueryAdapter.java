package minDb.SqlQueryParser.Adapter.Insert;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;

/**
 * IInsertQueryAdapter
 */
public interface IInsertQueryAdapter {
    public List<Object> getInsertValues(net.sf.jsqlparser.statement.insert.Insert insertStatement) throws ValidationException;
    public List<String> getInsertColumns(net.sf.jsqlparser.statement.insert.Insert insertStatement) throws ValidationException; 
}