package minDb.DataProvider.Logic;

import javax.xml.crypto.Data;

import minDb.Core.Components.IInsertQueryExecutor;
import minDb.Core.Data.IRawTableWriter;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Queries.InsertQuery;

/**
 * InsertExecutor
 */
public class InsertExecutor implements IInsertQueryExecutor {
    private IRawTableWriter _writer;

	public InsertExecutor(IRawTableWriter writer) {
        _writer = writer;
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

        _writer.writeTo(tableInfo, dbFolder, insert.get_insertValues());
    }    
}