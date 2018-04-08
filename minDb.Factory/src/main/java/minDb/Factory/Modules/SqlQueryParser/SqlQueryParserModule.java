package minDb.Factory.Modules.SqlQueryParser;

import com.google.inject.AbstractModule;

import minDb.Core.Components.IQueryParser;
import minDb.SqlQueryParser.Adapter.Create.CreateQueryFinder;
import minDb.SqlQueryParser.Adapter.Create.ICreateQueryAdapter;
import minDb.SqlQueryParser.Adapter.Insert.IInsertQueryAdapter;
import minDb.SqlQueryParser.Adapter.Primitives.IPrimitivesAdapter;
import minDb.SqlQueryParser.Adapter.Primitives.SqlPrimitivesAdapter;
import minDb.SqlQueryParser.Adapter.Select.IJoinAdapter;
import minDb.SqlQueryParser.Adapter.Select.ISelectAdapter;
import minDb.SqlQueryParser.Adapter.Select.IWhereConditionAdapter;

/**
 * SqlQueryParserModule
 */
public class SqlQueryParserModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ICreateQueryAdapter.class).to(minDb.SqlQueryParser.Adapter.Create.CreateQueryFinder.class);
		bind(ICreateQueryAdapter.class).to(CreateQueryFinder.class);
		bind(IPrimitivesAdapter.class).to(SqlPrimitivesAdapter.class);
		
		bind(IInsertQueryAdapter.class).toProvider(InsertAdapterProvider.class);
		bind(ISelectAdapter.class).toProvider(SelectAdapterProvider.class);
		bind(IWhereConditionAdapter.class).toProvider(WhereAdapterProvider.class);
		bind(IJoinAdapter.class).toProvider(JoinsAdapterProvider.class);

		bind(IQueryParser.class).toProvider(QueryParserProvider.class);
	}
}