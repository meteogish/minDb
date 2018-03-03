package minDb.Core.Components;

import minDb.Core.QueryModels.Query;

/**
 * IQueryParser
 */
public interface IQueryParser {
    Query parse(String statement);
}