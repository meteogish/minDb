package minDb.Factory.Modules.SqlQueryParser;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.SqlQueryParser.Adapter.From.IFromTableAdapter;
import minDb.SqlQueryParser.Adapter.Select.IJoinAdapter;
import minDb.SqlQueryParser.Adapter.Select.JoinsFinder;

/**
 * IJoinsAdapterProvider
 */
public class JoinsAdapterProvider implements Provider<IJoinAdapter> {

    private IFromTableAdapter _fromTableAdapter;
    
    @Inject
    public JoinsAdapterProvider(IFromTableAdapter fromTableAdapter) {
        _fromTableAdapter = fromTableAdapter;
    }

	public IJoinAdapter get() {
		return new JoinsFinder(_fromTableAdapter);
	}
}