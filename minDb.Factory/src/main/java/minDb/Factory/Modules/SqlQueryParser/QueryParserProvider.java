package minDb.Factory.Modules.SqlQueryParser;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.Core.Components.IQueryParser;
import minDb.SqlQueryParser.QueryParser;
import minDb.SqlQueryParser.Adapter.Create.ICreateQueryAdapter;
import minDb.SqlQueryParser.Adapter.Insert.IInsertQueryAdapter;
import minDb.SqlQueryParser.Adapter.Primitives.IPrimitivesAdapter;
import minDb.SqlQueryParser.Adapter.Select.IJoinAdapter;
import minDb.SqlQueryParser.Adapter.Select.ISelectAdapter;
import minDb.SqlQueryParser.Adapter.Select.IWhereConditionAdapter;

/**
 * SqlQueryParserProvider
 */
public class QueryParserProvider implements Provider<IQueryParser>{
    private ICreateQueryAdapter _createColumnsFinder;
    private IPrimitivesAdapter _primitivesAdapter;
    private IInsertQueryAdapter _insertValuesFinder;
    private ISelectAdapter _selectColumnsFinder;
    private IJoinAdapter _joinsFinder;
    private IWhereConditionAdapter _whereFinder;

    @Inject
    public QueryParserProvider(
        ICreateQueryAdapter createFinder,
        IPrimitivesAdapter primitivesAdapter,
        IInsertQueryAdapter insertValuesFinder,
        ISelectAdapter selectColumnsFinder,
        IJoinAdapter joinsFinder,
        IWhereConditionAdapter whereFinder) {
        _createColumnsFinder = createFinder;
        _primitivesAdapter = primitivesAdapter;
        _insertValuesFinder = insertValuesFinder;
        _selectColumnsFinder = selectColumnsFinder;
        _joinsFinder = joinsFinder;
        _whereFinder = whereFinder;
    }

	public IQueryParser get() {
		return new QueryParser(_createColumnsFinder, _primitivesAdapter, _insertValuesFinder, _selectColumnsFinder, _joinsFinder, _whereFinder);
    }
}