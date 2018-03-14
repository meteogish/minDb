package minDb.Core.Components;

import minDb.Core.MetaInfo.DatabaseMetaInfo;

/**
 * IMetaInfoProvider
 */
public interface IMetaInfoRepository {
    DatabaseMetaInfo getDatabaseMetaInfo(String path);   
}   