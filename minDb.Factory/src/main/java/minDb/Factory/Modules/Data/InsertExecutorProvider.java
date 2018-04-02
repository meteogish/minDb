package minDb.Factory.Modules.Data;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.Core.Components.IInsertQueryExecutor;
import minDb.Core.Components.Data.IRawTableWriter;
import minDb.Core.Components.Data.ITableFileProvider;
import minDb.DataProvider.Logic.InsertExecutor;

/**
 * InsertExecutorProvider
 */
public class InsertExecutorProvider implements Provider<IInsertQueryExecutor> {
    private IRawTableWriter _writer;
	private ITableFileProvider _tableFileProvider;


    @Inject
	public InsertExecutorProvider(IRawTableWriter writer,  ITableFileProvider tableFileProvider) {
        _writer = writer;
        _tableFileProvider = tableFileProvider;
    }

	public IInsertQueryExecutor get() {
		return new InsertExecutor(_writer, _tableFileProvider);
	}
    

}