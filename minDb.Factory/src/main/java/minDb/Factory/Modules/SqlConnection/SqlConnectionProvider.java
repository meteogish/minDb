package minDb.Factory.Modules.SqlConnection;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.Connection.SqlConnection;
import minDb.Core.Components.IDbConnection;
import minDb.Core.Components.IInsertQueryExecutor;
import minDb.Core.Components.IMetaInfoRepository;
import minDb.Core.Components.IQueryParser;
import minDb.Core.Components.ISelectQueryExecutor;
import minDb.Core.Components.Data.ITableFileProvider;

/**
 * SqlConnectionProvider
 */
public class SqlConnectionProvider implements Provider<IDbConnection> {
    private IQueryParser _queryParser;
    private IMetaInfoRepository _metaInfoRepository;
    private ISelectQueryExecutor _selectQueryExecutor;
    private IInsertQueryExecutor _insertQueryExecutor;
	private ITableFileProvider _tableFileProvider;

    @Inject
    public SqlConnectionProvider(IQueryParser queryParser, IMetaInfoRepository metaInfoRepository,
            ISelectQueryExecutor selectQueryExecutor, IInsertQueryExecutor insertQueryExecutor, ITableFileProvider tableFileProvider) {
        _queryParser = queryParser;
        _metaInfoRepository = metaInfoRepository;
        _selectQueryExecutor = selectQueryExecutor;
        _insertQueryExecutor = insertQueryExecutor;
        _tableFileProvider = tableFileProvider;
    }

    public IDbConnection get() {
        return new SqlConnection(_queryParser, _metaInfoRepository, _selectQueryExecutor, _insertQueryExecutor, _tableFileProvider);
    }
}