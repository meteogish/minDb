package minDb.Factory.Modules;

import com.google.inject.AbstractModule;

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
	}    
}