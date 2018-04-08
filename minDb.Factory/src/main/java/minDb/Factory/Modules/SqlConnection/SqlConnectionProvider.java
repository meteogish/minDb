package minDb.Factory.Modules.SqlConnection;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.Connection.SqlConnection;
import minDb.Core.Components.IDbConnection;
import minDb.Core.Components.IInsertQueryExecutor;
import minDb.Core.Components.IMetaInfoRepository;
import minDb.Core.Components.IQueryParser;
import minDb.Core.Components.ISelectQueryExecutor;
import minDb.Core.Components.IUpdateQueryExecutor;
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
	private IUpdateQueryExecutor _updateExecutor;

    @Inject
    public SqlConnectionProvider(IQueryParser queryParser, IMetaInfoRepository metaInfoRepository,
            ISelectQueryExecutor selectQueryExecutor, IInsertQueryExecutor insertQueryExecutor, ITableFileProvider tableFileProvider, IUpdateQueryExecutor updateExecutor) {
        _queryParser = queryParser;
        _metaInfoRepository = metaInfoRepository;
        _selectQueryExecutor = selectQueryExecutor;
        _insertQueryExecutor = insertQueryExecutor;
        _tableFileProvider = tableFileProvider;
        _updateExecutor = updateExecutor;
    }

    public IDbConnection get() {
        return new SqlConnection(_queryParser, 
                                _metaInfoRepository,
                                _selectQueryExecutor,
                                _insertQueryExecutor,
                                _tableFileProvider,
                                _updateExecutor);
    }
}