package minDb.Core.Components;

import minDb.Core.QueryModels.BaseQuery;

/**
 * IQueryParser
 */
public interface IQueryParser {
    BaseQuery parse(String statement);
}