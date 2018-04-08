package minDb.Core.Components;

import java.io.File;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;

/**
 * IMetaInfoProvider
 */
public interface IMetaInfoRepository {
    DatabaseMetaInfo getDatabaseMetaInfo(File dbFile) throws ValidationException;    
    DatabaseMetaInfo getDatabaseMetaInfo(String path) throws ValidationException;

    void saveDatabaseMetaInfo(DatabaseMetaInfo info, String folderPath, String name) throws ValidationException;
    void saveDatabaseMetaInfo(DatabaseMetaInfo info, File dbFile) throws ValidationException;
}   