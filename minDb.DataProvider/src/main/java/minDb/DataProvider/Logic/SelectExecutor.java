package minDb.DataProvider.Logic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.util.ElementScanner6;

import minDb.Core.Components.ISelectQueryExecutor;
import minDb.Core.Components.Data.IDataTable;
import minDb.Core.Components.Data.IRawTableReader;
import minDb.Core.Components.Data.ITableFileProvider;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnMetaInfo;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Column;
import minDb.Core.QueryModels.Join;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.Table;
import minDb.Core.QueryModels.Conditions.JoinColumnCondition;
import minDb.Core.QueryModels.Queries.SelectQuery;
import minDb.DataProvider.Data.Models.DataTable;
import minDb.Extensions.StringExtenstions;

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
    public IDataTable execute(SelectQuery selectQuery, DatabaseMetaInfo dbInfo, String dbFolder)
            throws ValidationException {
        
        DataTable data = readTable(selectQuery.get_table(), dbInfo, selectQuery, dbFolder);
        for (Join join : selectQuery.get_joins()) 
        {
            DataTable joinTable = readTable(join.get_table(), dbInfo, selectQuery, dbFolder);
            data.join(joinData, join.get_conditions());
        }
        return data;
    }

    private DataTable readTable(Table table, DatabaseMetaInfo dbInfo, SelectQuery selectQuery, String dbFolder) throws ValidationException
    {
        TableMetaInfo tableInfo = getTableMetaInfo(dbInfo, table.get_name());
        File tableFile = _tableFileProvider.getTableFile(tableInfo.get_tableName(), dbFolder, false);
        List<Column> selectColumns = getSelectColumns(selectQuery, table);
        List<List<Object>> data = null;
        if(selectColumns == null)
        {
            data = _reader.readFrom(tableInfo, tableFile);            
        }
        else
        {
            List<Integer> select = getIndexesOfSelectedColumns(tableInfo, selectColumns);
            data = _reader.readFrom(tableInfo, tableFile, select);
        }
        
        return new DataTable(selectColumns, data);
    }


    private List<Integer> getIndexesOfSelectedColumns(TableMetaInfo tableInfo, List<Column> selectedColumns) {
        List<Integer> indexes = new ArrayList<Integer>();

        for (int i = 0; i < tableInfo.get_columnsInfo().size(); i++) {
            for(Column c : selectedColumns)
            {
                if(c.get_name().equalsIgnoreCase(tableInfo.get_columnsInfo().get(i).get_name()))
                {
                    indexes.add(i);
                    break;
                }
            }
        }
        return indexes;
    }

    private TableMetaInfo getTableMetaInfo(DatabaseMetaInfo dbInfo, String tableName) throws ValidationException
    {
       TableMetaInfo table = dbInfo.get_tables().stream()
                .filter(t -> t.get_tableName().equalsIgnoreCase(tableName)).findFirst().get();
        if (table == null) {
            throw new ValidationException("Can not find table " + tableName + " in the db");
        }
        return table;
    }

    private List<Column> getSelectColumns(SelectQuery selectQuery, Table table) throws ValidationException {
        if (selectQuery.get_select().isEmpty()) {
            return null;
        }
        List<Column> columns = new ArrayList<Column>();

        for (Join j : selectQuery.get_joins()) {
            if (j.get_table().get_alias().equalsIgnoreCase(table.get_alias())) {
                for (JoinColumnCondition c : j.get_conditions()) {
                    Column leftColumn = c.get_leftColumn();
                    Column rightColumn = c.get_rightColumn();
                    if (leftColumn.get_table().get_alias().equals(table.get_alias())) {
                        if (!columns.contains(leftColumn)) {
                            columns.add(leftColumn);
                        }
                    } else if (rightColumn.get_table().get_alias().equals(table.get_alias())) {
                        columns.add(rightColumn);
                    } else {
                        throw new ValidationException(
                                "Columns in JOIN clause don't hava the same alias as join table.");
                    }
                }
            }
        }

        for (SelectColumn sc : selectQuery.get_select()) {
            if (sc.get_table().get_name().equalsIgnoreCase(table.get_name())) {
                if (!columns.contains(sc)) {
                    columns.add(sc);
                }
            }
        }
        return columns;
    }

}