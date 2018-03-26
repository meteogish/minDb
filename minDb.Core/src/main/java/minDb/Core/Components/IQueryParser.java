package minDb.Core.Components;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Queries.Query;

/**
 * IQueryParser
 */
public interface IQueryParser {
    Query parse(String statement) throws ValidationException;
}