package minDb.DataProvider.Logic;

import java.util.List;

import minDb.Core.Components.ISelectQueryExecutor;
import minDb.Core.Components.Data.IDataTable;
import minDb.Core.Components.Data.IRawTableReader;
import minDb.Core.Components.Data.ITableFileProvider;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Queries.SelectQuery;
import minDb.DataProvider.Data.Models.DataTable;

/**
 * SelectExecutor
 */
public class SelectExecutor implements ISelectQueryExecutor {

    private IRawTableReader _reader;
	private ITableFileProvider _tableFileProvider;

	public SelectExecutor(IRawTableReader reader, ITableFileProvider tableFileProvider) {
        _reader = reader;
        _tableFileProvider = tableFileProvider;
    }

	@Override
	public IDataTable execute(SelectQuery selectQuery, DatabaseMetaInfo dbInfo, String dbFolder) throws ValidationException {
		TableMetaInfo tableInfo = dbInfo.get_tables().stream().filter(t -> t.get_tableName().equalsIgnoreCase(selectQuery.get_table().get_name())).findFirst().get();
        if(tableInfo == null)
        {
            throw new ValidationException("Can not find table " + selectQuery.get_table().get_name() + " in the db");
        }

        List<List<Object>> readFrom = _reader.readFrom(tableInfo, _tableFileProvider.getTableFile(tableInfo.get_tableName(), dbFolder, false));
        //dataTable.select(selectQuery.get_select());
        return new DataTable(null, readFrom);
	}

    
}