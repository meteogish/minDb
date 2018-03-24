package minDb.Factory.Modules.SqlQueryParser;

import com.google.inject.AbstractModule;

import minDb.Core.Components.IQueryParser;
import minDb.SqlQueryParser.Adapter.Create.CreateQueryFinder;
import minDb.SqlQueryParser.Adapter.Create.ICreateQueryAdapter;
import minDb.SqlQueryParser.Adapter.From.FromTableFinder;
import minDb.SqlQueryParser.Adapter.From.IFromTableAdapter;
import minDb.SqlQueryParser.Adapter.Insert.IInsertQueryAdapter;
import minDb.SqlQueryParser.Adapter.Insert.InsertQueryFinder;
import minDb.SqlQueryParser.Adapter.Select.IJoinAdapter;
import minDb.SqlQueryParser.Adapter.Select.ISelectAdapter;
import minDb.SqlQueryParser.Adapter.Select.SelectColumnsFinder;

/**
 * SqlQueryParserModule
 */
public class SqlQueryParserModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ICreateQueryAdapter.class).to(minDb.SqlQueryParser.Adapter.Create.CreateQueryFinder.class);
		bind(ICreateQueryAdapter.class).to(CreateQueryFinder.class);
		bind(IFromTableAdapter.class).to(FromTableFinder.class);
		bind(IInsertQueryAdapter.class).to(InsertQueryFinder.class);
		bind(ISelectAdapter.class).to(SelectColumnsFinder.class);
		bind(IJoinAdapter.class).toProvider(JoinsAdapterProvider.class);

		bind(IQueryParser.class).toProvider(QueryParserProvider.class);
	}
}