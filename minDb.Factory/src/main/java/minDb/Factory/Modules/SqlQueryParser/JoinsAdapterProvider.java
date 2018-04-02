package minDb.Factory.Modules.SqlQueryParser;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.SqlQueryParser.Adapter.Primitives.IPrimitivesAdapter;
import minDb.SqlQueryParser.Adapter.Select.IJoinAdapter;
import minDb.SqlQueryParser.Adapter.Select.JoinsFinder;

/**
 * IJoinsAdapterProvider
 */
public class JoinsAdapterProvider implements Provider<IJoinAdapter> {

    private IPrimitivesAdapter _primitivesAdapter;

    @Inject
	public JoinsAdapterProvider(IPrimitivesAdapter primitivesAdapter) {
        _primitivesAdapter = primitivesAdapter;
    }

	public IJoinAdapter get() {
		return new JoinsFinder(_primitivesAdapter);
	}
}