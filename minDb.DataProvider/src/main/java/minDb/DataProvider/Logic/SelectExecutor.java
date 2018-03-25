package minDb.DataProvider.Logic;

import minDb.Core.Components.ISelectQueryExecutor;
import minDb.Core.Data.IDataTable;
import minDb.Core.Data.IRawTableReader;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Queries.SelectQuery;

/**
 * SelectExecutor
 */
public class SelectExecutor implements ISelectQueryExecutor {

    private IRawTableReader _reader;

	public SelectExecutor(IRawTableReader reader) {
        _reader = reader;
    }

	@Override
	public IDataTable execute(SelectQuery selectQuery, DatabaseMetaInfo dbInfo, String dbFolder) throws ValidationException {
		TableMetaInfo tableInfo = dbInfo.get_tables().stream().filter(t -> t.get_tableName().equalsIgnoreCase(selectQuery.get_table().get_name())).findFirst().get();
        if(tableInfo == null)
        {
            throw new ValidationException("Can not find table " + selectQuery.get_table().get_name() + " in the db");
        }

        IDataTable dataTable = _reader.readFrom(tableInfo, dbFolder);
        dataTable.select(selectQuery.get_select());
        return dataTable;
	}

    
}