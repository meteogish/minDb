package minDb.Factory.Modules.Data;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.Core.Components.Data.IRawTableWriter;
import minDb.Core.Components.Data.ITypeSizeProvider;
import minDb.DataProvider.Data.IO.TableWriter;

/**
 * TableWriterProvider
 */
public class TableWriterProvider  implements Provider<IRawTableWriter> {

    private ITypeSizeProvider _typeSizeProvider;

	@Inject
    public TableWriterProvider(ITypeSizeProvider typeSizeProvider) {
        _typeSizeProvider = typeSizeProvider;
    }

	public IRawTableWriter get() {
		return new TableWriter(_typeSizeProvider);
	}

    
}