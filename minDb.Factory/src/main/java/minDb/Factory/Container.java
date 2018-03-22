package minDb.Factory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import minDb.Factory.Modules.*;

/**
 * Container
 */
public class Container {
    public Injector getInjector()
    {
       return Guice.createInjector(new MetaInfoRepositoryModule(), new SqlQueryParserModule());
    }
    
}