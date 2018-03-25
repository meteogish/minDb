package minDb.Core.Components;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Core.QueryModels.Queries.InsertQuery;

/**
 * IInsertQueryExecutor
 */
public interface IInsertQueryExecutor {
    void execute(InsertQuery insert, DatabaseMetaInfo dbInfo, String dbFolder) throws ValidationException;    
}