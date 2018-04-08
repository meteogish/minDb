package minDb.Factory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import minDb.Factory.Modules.MetaInfoRepositoryModule;
import minDb.Factory.Modules.Data.DataProviderModule;
import minDb.Factory.Modules.SqlConnection.SqlConnectionModule;
import minDb.Factory.Modules.SqlQueryParser.SqlQueryParserModule;

/**
 * Container
 */
public class Container {
    public Injector getInjector()
    {
       return Guice.createInjector(new MetaInfoRepositoryModule(),
        new SqlQueryParserModule(),
         new DataProviderModule(),
         new SqlConnectionModule());
    }    
}