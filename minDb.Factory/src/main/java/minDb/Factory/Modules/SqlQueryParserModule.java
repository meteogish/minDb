package minDb.Factory.Modules;

import java.lang.reflect.Constructor;

import com.google.inject.AbstractModule;

import minDb.Core.Components.IQueryParser;
import minDb.QueryBuilder.CreateTable.CreateQueryFinder;
import minDb.QueryBuilder.Select.FromTableFinder;
import minDb.QueryBuilder.Select.JoinsFinder;
import minDb.QueryBuilder.Select.SelectColumnsFinder;
import minDb.SqlQueryParser.QueryParser;

/**
 * SqlQueryParserModule
 */
public class SqlQueryParserModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CreateQueryFinder.class);
		bind(FromTableFinder.class);
		bind(SelectColumnsFinder.class);
		bind(JoinsFinder.class);
		Constructor<QueryParser> constructor = null;
		try {
			constructor = QueryParser.class.getConstructor(
			CreateQueryFinder.class,
			FromTableFinder.class,
			SelectColumnsFinder.class,
			JoinsFinder.class);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bind(IQueryParser.class).toConstructor(constructor);
	}
}