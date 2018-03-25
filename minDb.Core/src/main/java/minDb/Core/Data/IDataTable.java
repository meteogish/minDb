package minDb.Core.Data;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.SelectColumn;

/**
 * IDataTable
 */
public interface IDataTable {
    IDataRow get(int i);
    List<String> getHeader();
    void select(List<SelectColumn> selectColumns) throws ValidationException;
    void print();
    //void filter(Predicate<IDataRow> predicate);
}