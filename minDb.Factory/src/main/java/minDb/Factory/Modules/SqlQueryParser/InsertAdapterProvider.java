package minDb.Factory.Modules.SqlQueryParser;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.SqlQueryParser.Adapter.Insert.IInsertQueryAdapter;
import minDb.SqlQueryParser.Adapter.Insert.InsertQueryFinder;
import minDb.SqlQueryParser.Adapter.Primitives.IPrimitivesAdapter;

/**
 * InsertAdapterProvider
 */
public class InsertAdapterProvider implements Provider<IInsertQueryAdapter> {

    private IPrimitivesAdapter _primitivesAdapter;

    @Inject
	public InsertAdapterProvider(IPrimitivesAdapter primitivesAdapter) {
        _primitivesAdapter = primitivesAdapter;
    }

	public IInsertQueryAdapter get() {
		return new InsertQueryFinder(_primitivesAdapter);
	}    
}