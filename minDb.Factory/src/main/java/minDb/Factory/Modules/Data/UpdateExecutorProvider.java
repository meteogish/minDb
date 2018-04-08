package minDb.Factory.Modules.Data;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.Core.Components.IUpdateQueryExecutor;
import minDb.Core.Components.Data.IRawTableReader;
import minDb.Core.Components.Data.IRawTableWriter;
import minDb.Core.Components.Data.ITableFileProvider;
import minDb.DataProvider.Logic.UpdateExecutor;

/**
 * UpdateExecutorProvider
 */
public class UpdateExecutorProvider implements Provider<IUpdateQueryExecutor> {

    private IRawTableReader _reader;
	private IRawTableWriter _writer;
	private ITableFileProvider _fileProvider;

	@Inject
    public UpdateExecutorProvider(IRawTableReader reader, IRawTableWriter writer, ITableFileProvider fileProvider) {
        _reader = reader;
        _writer = writer;
        _fileProvider = fileProvider;
    }

	public IUpdateQueryExecutor get() {
		return new UpdateExecutor(_reader, _writer, _fileProvider);
	}

    
}