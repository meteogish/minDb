package minDb.Factory.Modules.SqlConnection;

import com.google.inject.AbstractModule;

import minDb.Core.Components.IDbConnection;

/**
 * SqlConnectionModule
 */
public class SqlConnectionModule extends AbstractModule {

	@Override
	protected void configure() {
        bind(IDbConnection.class).toProvider(SqlConnectionProvider.class);
    }    
}