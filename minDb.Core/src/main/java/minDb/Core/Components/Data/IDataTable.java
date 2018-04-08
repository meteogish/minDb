package minDb.Core.Components.Data;

import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.Conditions.ICondition;

/**
 * IDataTable
 */
public interface IDataTable {
    void print();
    void select(List<SelectColumn> selectColumns) throws ValidationException;
    void filter(ICondition condition)  throws ValidationException;
}