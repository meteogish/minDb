package minDb.Core.Components.Data;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnType;

/**
 * ITypeSizeProvider
 */
public interface ITypeSizeProvider {
    int getBytesSize(ColumnType columnType) throws ValidationException;   
}