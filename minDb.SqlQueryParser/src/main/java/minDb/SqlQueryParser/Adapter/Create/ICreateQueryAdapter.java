package minDb.SqlQueryParser.Adapter.Create;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnMetaInfo;

/**
 * ICreateQueryAdapter
 */
public interface ICreateQueryAdapter {
    public List<ColumnMetaInfo> getCreateTableColumns(net.sf.jsqlparser.statement.create.table.CreateTable createQuery) throws ValidationException;
}