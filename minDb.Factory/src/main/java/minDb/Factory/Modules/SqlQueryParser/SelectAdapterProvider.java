package minDb.Factory.Modules.SqlQueryParser;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.SqlQueryParser.Adapter.Primitives.IPrimitivesAdapter;
import minDb.SqlQueryParser.Adapter.Select.ISelectAdapter;
import minDb.SqlQueryParser.Adapter.Select.SelectColumnsFinder;

/**
 * SelectAdapterProvider
 */
public class SelectAdapterProvider implements Provider<ISelectAdapter> {

    private IPrimitivesAdapter _primitivesAdapter;

    @Inject
	public SelectAdapterProvider(IPrimitivesAdapter primitivesAdapter) {
        _primitivesAdapter = primitivesAdapter;
    }

	public ISelectAdapter get() {
		return new SelectColumnsFinder(_primitivesAdapter);
	}    
}