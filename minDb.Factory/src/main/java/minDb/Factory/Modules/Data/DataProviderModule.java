package minDb.Factory.Modules.Data;

import com.google.inject.AbstractModule;

import minDb.Core.Components.IInsertQueryExecutor;
import minDb.Core.Components.ISelectQueryExecutor;
import minDb.Core.Components.Data.IRawTableReader;
import minDb.Core.Components.Data.IRawTableWriter;
import minDb.Core.Components.Data.ITableFileProvider;
import minDb.Core.Components.Data.ITypeSizeProvider;
import minDb.DataProvider.Data.IO.TableFileProvider;
import minDb.DataProvider.Data.TypeSizeProvider;

/**
 * DataModule
 */
public class DataProviderModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ITypeSizeProvider.class).to(TypeSizeProvider.class);
		bind(ITableFileProvider.class).to(TableFileProvider.class);

        bind(IRawTableWriter.class).toProvider(TableWriterProvider.class);
		bind(IRawTableReader.class).toProvider(TableReaderProvider.class);
		
		bind(ISelectQueryExecutor.class).toProvider(SelectExecutorProvider.class);
		bind(IInsertQueryExecutor.class).toProvider(InsertExecutorProvider.class);
	}    
}