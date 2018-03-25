package minDb.Core.Components;

import minDb.Core.Data.IDataTable;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Core.QueryModels.Queries.SelectQuery;

/**
 * ISelectQueryExecutor
 */
public interface ISelectQueryExecutor {
    IDataTable execute(SelectQuery selectQuery, DatabaseMetaInfo dbInfo, String dbFolder) throws ValidationException;    
}