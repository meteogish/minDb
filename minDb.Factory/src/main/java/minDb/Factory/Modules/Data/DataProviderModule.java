package minDb.Factory.Modules.Data;

import com.google.inject.AbstractModule;

import minDb.Core.Components.IInsertQueryExecutor;
import minDb.Core.Components.ISelectQueryExecutor;
import minDb.Core.Data.IRawTableReader;
import minDb.Core.Data.IRawTableWriter;
import minDb.DataProvider.Data.TableReader;
import minDb.DataProvider.Data.TableWriter;

/**
 * DataModule
 */
public class DataProviderModule extends AbstractModule {

	@Override
	protected void configure() {
        bind(IRawTableWriter.class).to(TableWriter.class);
		bind(IRawTableReader.class).to(TableReader.class);
		
		bind(ISelectQueryExecutor.class).toProvider(SelectExecutorProvider.class);
		bind(IInsertQueryExecutor.class).toProvider(InsertExecutorProvider.class);
	}    
}