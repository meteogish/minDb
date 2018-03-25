package minDb.Factory.Modules.Data;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.Core.Components.IInsertQueryExecutor;
import minDb.Core.Data.IRawTableWriter;
import minDb.DataProvider.Logic.InsertExecutor;

/**
 * InsertExecutorProvider
 */
public class InsertExecutorProvider implements Provider<IInsertQueryExecutor> {
    private IRawTableWriter _writer;

    @Inject
	public InsertExecutorProvider(IRawTableWriter writer) {
        _writer = writer;
    }

	public IInsertQueryExecutor get() {
		return new InsertExecutor(_writer);
	}
    

}