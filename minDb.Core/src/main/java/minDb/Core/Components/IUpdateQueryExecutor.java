package minDb.Core.Components;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Core.QueryModels.Queries.UpdateQuery;

/**
 * IUpdateQueryExecutor
 */
public interface IUpdateQueryExecutor {
    void execute(UpdateQuery insert, DatabaseMetaInfo dbInfo, String dbFolder) throws ValidationException;
}