package minDb.Core.QueryModels.Conditions;

import java.util.List;
import java.util.function.Function;

import minDb.Core.Components.Data.IDataRow;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Column;

/**
 * ICondition
 */
public interface ICondition {
	Boolean apply(IDataRow row, Function<Column, Integer> columnToIndexMapper) throws ValidationException;
	List<Column> getConditionColumns();
}