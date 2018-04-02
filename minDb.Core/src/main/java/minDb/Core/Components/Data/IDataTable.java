package minDb.Core.Components.Data;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.SelectColumn;

/**
 * IDataTable
 */
public interface IDataTable {
    void print();
    // IDataRow get(int i);
    // List<String> getHeader();
    // void select(List<SelectColumn> selectColumns) throws ValidationException;
    // void print();
    // Integer getColumnsCount();
    //void filter(Predicate<IDataRow> predicate);
}