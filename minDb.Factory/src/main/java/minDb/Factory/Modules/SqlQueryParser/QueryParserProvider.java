package minDb.Factory.Modules.SqlQueryParser;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.Core.Components.IQueryParser;
import minDb.SqlQueryParser.QueryParser;
import minDb.SqlQueryParser.Adapter.Create.ICreateQueryAdapter;
import minDb.SqlQueryParser.Adapter.From.IFromTableAdapter;
import minDb.SqlQueryParser.Adapter.Insert.IInsertQueryAdapter;
import minDb.SqlQueryParser.Adapter.Select.IJoinAdapter;
import minDb.SqlQueryParser.Adapter.Select.ISelectAdapter;

/**
 * SqlQueryParserProvider
 */
public class QueryParserProvider implements Provider<IQueryParser>{
    private ICreateQueryAdapter _createColumnsFinder;
    private IFromTableAdapter _fromtableFinder;
    private IInsertQueryAdapter _insertValuesFinder;
    private ISelectAdapter _selectColumnsFinder;
    private IJoinAdapter _joinsFinder;

    @Inject
    public QueryParserProvider(
        ICreateQueryAdapter createFinder,
        IFromTableAdapter fromtableFinder,
        IInsertQueryAdapter insertValuesFinder,
        ISelectAdapter selectColumnsFinder,
        IJoinAdapter joinsFinder) {
        _createColumnsFinder = createFinder;
        _fromtableFinder = fromtableFinder;
        _insertValuesFinder = insertValuesFinder;
        _selectColumnsFinder = selectColumnsFinder;
        _joinsFinder = joinsFinder;
    }

	public IQueryParser get() {
		return new QueryParser(_createColumnsFinder, _fromtableFinder, _insertValuesFinder, _selectColumnsFinder, _joinsFinder);
    }    
}