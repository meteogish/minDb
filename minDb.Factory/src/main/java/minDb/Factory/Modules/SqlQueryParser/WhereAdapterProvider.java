package minDb.Factory.Modules.SqlQueryParser;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.SqlQueryParser.Adapter.Primitives.IPrimitivesAdapter;
import minDb.SqlQueryParser.Adapter.Select.IWhereConditionAdapter;
import minDb.SqlQueryParser.Adapter.Select.WhereFinder;

/**
 * WhereAdapterProvider
 */
public class WhereAdapterProvider implements Provider<IWhereConditionAdapter> {
    
    private IPrimitivesAdapter _primitivesAdapter;

    @Inject
	public WhereAdapterProvider(IPrimitivesAdapter primitivesAdapter) {
        _primitivesAdapter = primitivesAdapter;
    }
    
	public IWhereConditionAdapter get() {
		return new WhereFinder(_primitivesAdapter);
	}    
}