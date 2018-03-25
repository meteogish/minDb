package minDb.Core.Data;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.TableMetaInfo;

/**
 * ITableWriter
 */
public interface IRawTableWriter {
    void writeTo(TableMetaInfo tableInfo, String dbFolder, List<Object> values) throws ValidationException;
}