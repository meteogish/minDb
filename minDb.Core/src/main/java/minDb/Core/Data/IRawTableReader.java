package minDb.Core.Data;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.TableMetaInfo;

/**
 * ITableReader
 */
public interface IRawTableReader {
    IDataTable readFrom(TableMetaInfo tableInfo, String dbFolder) throws ValidationException;    
}