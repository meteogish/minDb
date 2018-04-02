package minDb.Factory.Modules.Data;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.Core.Components.Data.IRawTableReader;
import minDb.Core.Components.Data.ITypeSizeProvider;
import minDb.DataProvider.Data.IO.TableReader;

/**
 * TableReaderProvider
 */
public class TableReaderProvider implements Provider<IRawTableReader> {
    private ITypeSizeProvider _typeSizeProvider;

	@Inject
    public TableReaderProvider(ITypeSizeProvider typeSizeProvider) {
        _typeSizeProvider = typeSizeProvider;
    }

	public IRawTableReader get() {
		return new TableReader(_typeSizeProvider);
	}
    
}