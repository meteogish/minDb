package minDb.Factory.Modules.Data;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.Core.Components.ISelectQueryExecutor;
import minDb.Core.Components.Data.IRawTableReader;
import minDb.Core.Components.Data.ITableFileProvider;
import minDb.DataProvider.Logic.SelectExecutor;

/**
 * SelectExecutorProvider
 */
public class SelectExecutorProvider implements Provider<ISelectQueryExecutor> {

    private IRawTableReader _reader;
	private ITableFileProvider _tableFileProvider;

	@Inject
    public SelectExecutorProvider(IRawTableReader reader,  ITableFileProvider tableFileProvider) {
        _reader = reader;
        _tableFileProvider = tableFileProvider;
        
    }

	public ISelectQueryExecutor get() {
		return new SelectExecutor(_reader, _tableFileProvider);
	}    
}