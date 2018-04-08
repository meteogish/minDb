package minDb.DataProvider.Logic;

import java.io.File;
import java.util.List;

import minDb.Core.Components.IUpdateQueryExecutor;
import minDb.Core.Components.Data.IRawTableReader;
import minDb.Core.Components.Data.IRawTableWriter;
import minDb.Core.Components.Data.ITableFileProvider;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Column;
import minDb.Core.QueryModels.Table;
import minDb.Core.QueryModels.Queries.UpdateQuery;
import minDb.DataProvider.Data.Models.DataRow;

/**
 * UpdateExecutor
 */
public class UpdateExecutor extends BaseExecutor implements IUpdateQueryExecutor {

    private IRawTableReader _reader;
    private IRawTableWriter _writer;
    private ITableFileProvider _tableFileProvider;

    public UpdateExecutor(IRawTableReader reader, IRawTableWriter writer, ITableFileProvider tableFileProvider) {
        _reader = reader;
        _writer = writer;
        _tableFileProvider = tableFileProvider;
    }

    @Override
    public void execute(UpdateQuery update, DatabaseMetaInfo dbInfo, String dbFolder) throws ValidationException {
        Table table = update.get_table();
        TableMetaInfo tableInfo = dbInfo.getTableMetaInfo(table.get_name());
        File tableFile = _tableFileProvider.getTableFile(tableInfo.get_tableName(), dbFolder, false);
        List<List<Object>> data = _reader.readFrom(tableInfo, tableFile);
        List<Integer> updateColumnsIndexes = getIndexesOfColumns(tableInfo, update.get_updateColumns());
        List<Column> header = tableInfo.getColumns(table);
        //TODO Check strings length are passing to type lenght
        for(List<Object> row : data)
        {
            Boolean canUpdate = true;
            if(update.get_where() != null)
            {
                canUpdate = update.get_where().apply(new DataRow(row), c -> header.indexOf(c));
            }
            if(canUpdate)
            {
                for(int i = 0; i < updateColumnsIndexes.size(); ++i)
                {
                    Integer columnIndex = updateColumnsIndexes.get(i);
					Object element = update.get_values().get(i);
					row.set(columnIndex, element);
                }
            }
        }

        _writer.writeTo(tableInfo, tableFile, data, false);
    }
}