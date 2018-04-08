package minDb.DataProvider.Logic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        for (Join join : selectQuery.get_joins()) {
            DataTable joinTable = readTable(join.get_table(), dbInfo, selectQuery, dbFolder);
            data.join(joinTable, join.get_conditions());
        }
        data.filter(selectQuery.get_where());
        data.select(selectQuery.get_select());
        return data;
    }

    private DataTable readTable(Table table, DatabaseMetaInfo dbInfo, SelectQuery selectQuery, String dbFolder)
            throws ValidationException {
        TableMetaInfo tableInfo = dbInfo.getTableMetaInfo(table.get_name());
        File tableFile = _tableFileProvider.getTableFile(tableInfo.get_tableName(), dbFolder, false);
        List<Column> usedColumns = getAllUsedColumns(selectQuery, table);
        List<List<Object>> data = null;
        if (usedColumns == null) {
            data = _reader.readFrom(tableInfo, tableFile);
            usedColumns = new ArrayList<>(tableInfo.get_columnsInfo().size());
            for (ColumnMetaInfo ci : tableInfo.get_columnsInfo()) {
                usedColumns.add(new Column(table, ci.get_name()));
            }
        } else {
            List<Integer> selectIndexes = getIndexesOfSelectedColumns(tableInfo, usedColumns);
            data = _reader.readFrom(tableInfo, tableFile, selectIndexes);
        }

        return new DataTable(usedColumns, data);
    }

    private List<Integer> getIndexesOfSelectedColumns(TableMetaInfo tableInfo, List<Column> selectedColumns) throws ValidationException {
        List<Integer> indexes = new ArrayList<Integer>();
        Boolean columnExists;
        for (Column c : selectedColumns) {
            columnExists = false;
            for (int i = 0; i < tableInfo.get_columnsInfo().size(); i++) {
                if (c.get_name().equalsIgnoreCase(tableInfo.get_columnsInfo().get(i).get_name())) {
                    indexes.add(i);
                    columnExists = true;
                    break;
                }
            }

            if(!columnExists)
            {
                throw new ValidationException("The column " + c.get_name() + " not exists in table " + c.get_table().get_name());
            }
        }
        return indexes;
    }

    private List<Column> getAllUsedColumns(SelectQuery selectQuery, Table table) throws ValidationException {
        if (selectQuery.get_select().isEmpty()) {
            return null;
        }
        List<Column> columns = new ArrayList<Column>();

        for (Join j : selectQuery.get_joins()) {
            for (JoinColumnCondition c : j.get_conditions()) {
                Column leftColumn = c.get_leftColumn();
                Column rightColumn = c.get_rightColumn();
                if (leftColumn.get_table().get_alias().equals(table.get_alias())) {
                    addSelectColumn(columns, leftColumn);
                } else if (rightColumn.get_table().get_alias().equals(table.get_alias())) {
                    addSelectColumn(columns, rightColumn);
                }
            }
        }

        for (SelectColumn sc : selectQuery.get_select()) {
            if (sc.get_table().get_name().equalsIgnoreCase(table.get_name())) {
                addSelectColumn(columns, sc);
            }
        }

        if (selectQuery.get_where() != null) {
            for (Column column : selectQuery.get_where().getConditionColumns()) {
                if (column.get_table().get_name().equalsIgnoreCase(table.get_name())) {
                    addSelectColumn(columns, column);
                }
            }
        }
        return columns;
    }

    private void addSelectColumn(List<Column> select, Column col)
    {
        if(!select.contains(col))
        {
            select.add(col);
        }
    }
}