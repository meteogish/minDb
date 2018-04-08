package minDb.DataProvider.Logic;

import java.util.ArrayList;
import java.util.List;

import minDb.Core.Components.IInsertQueryExecutor;
import minDb.Core.Components.Data.IRawTableWriter;
import minDb.Core.Components.Data.ITableFileProvider;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Queries.InsertQuery;

/**
 * InsertExecutor
 */
public class InsertExecutor implements IInsertQueryExecutor {
    private IRawTableWriter _writer;
	private ITableFileProvider _tableFileProvider;

	public InsertExecutor(IRawTableWriter writer, ITableFileProvider tableFileProvider) {
        _writer = writer;
        _tableFileProvider = tableFileProvider;
    }

	@Override
	public void execute(InsertQuery insert, DatabaseMetaInfo dbInfo, String dbFolder) throws ValidationException {
        if(insert.get_insertValues() == null || insert.get_insertValues().isEmpty())
        {
            throw new ValidationException("Nothing to insert");
        }

        TableMetaInfo tableInfo = dbInfo.get_tables().stream().filter(t -> t.get_tableName().equalsIgnoreCase(insert.get_table().get_name())).findFirst().get();
        if(tableInfo == null)
        {
            throw new ValidationException("Can not find table " + insert.get_table().get_name() + " in the db");
        }

        //TODO Columns validation

        List<List<Object>> rows = new ArrayList<List<Object>>();
        rows.add(insert.get_insertValues());

        _writer.writeTo(tableInfo, _tableFileProvider.getTableFile(tableInfo.get_tableName(), dbFolder, true), rows, true);
    }    
}