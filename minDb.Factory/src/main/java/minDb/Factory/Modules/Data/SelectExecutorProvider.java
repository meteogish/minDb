package minDb.Factory.Modules.Data;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.Core.Components.ISelectQueryExecutor;
import minDb.Core.Data.IRawTableReader;
import minDb.DataProvider.Logic.SelectExecutor;

/**
 * SelectExecutorProvider
 */
public class SelectExecutorProvider implements Provider<ISelectQueryExecutor> {

    private IRawTableReader _reader;

	@Inject
    public SelectExecutorProvider(IRawTableReader reader) {
        _reader = reader;
    }

	public ISelectQueryExecutor get() {
		return new SelectExecutor(_reader);
	}    
}