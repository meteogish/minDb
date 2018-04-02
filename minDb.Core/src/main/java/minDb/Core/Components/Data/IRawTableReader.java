package minDb.Core.Components.Data;

import java.io.File;
import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.TableMetaInfo;

/**
 * ITableReader
 */
public interface IRawTableReader {
    List<List<Object>> readFrom(TableMetaInfo tableInfo, File tableFile) throws ValidationException;
    List<List<Object>> readFrom(TableMetaInfo tableInfo, File tableFile, List<Integer> selectColumnIndexes) throws ValidationException;
}