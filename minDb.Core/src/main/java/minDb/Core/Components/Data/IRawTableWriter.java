package minDb.Core.Components.Data;

import java.io.File;
import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.TableMetaInfo;

/**
 * ITableWriter
 */
public interface IRawTableWriter {
    void writeTo(TableMetaInfo tableInfo, File tableFile, List<List<Object>> values, boolean append) throws ValidationException;
}