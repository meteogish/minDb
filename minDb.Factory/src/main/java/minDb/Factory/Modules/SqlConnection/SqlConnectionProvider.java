package minDb.Factory.Modules.SqlConnection;

import com.google.inject.Inject;
import com.google.inject.Provider;

import minDb.Connection.SqlConnection;
import minDb.Core.Components.IDbConnection;
import minDb.Core.Components.IInsertQueryExecutor;
import minDb.Core.Components.IMetaInfoRepository;
import minDb.Core.Components.IQueryParser;
import minDb.Core.Components.ISelectQueryExecutor;

/**
 * SqlConnectionProvider
 */
public class SqlConnectionProvider implements Provider<IDbConnection> {
    private IQueryParser _queryParser;
    private IMetaInfoRepository _metaInfoRepository;
    private ISelectQueryExecutor _selectQueryExecutor;
    private IInsertQueryExecutor _insertQueryExecutor;

    @Inject
    public SqlConnectionProvider(IQueryParser queryParser, IMetaInfoRepository metaInfoRepository,
            ISelectQueryExecutor selectQueryExecutor, IInsertQueryExecutor insertQueryExecutor) {
        _queryParser = queryParser;
        _metaInfoRepository = metaInfoRepository;
        _selectQueryExecutor = selectQueryExecutor;
        _insertQueryExecutor = insertQueryExecutor;
    }

    public IDbConnection get() {
        return new SqlConnection(_queryParser, _metaInfoRepository, _selectQueryExecutor, _insertQueryExecutor);
    }

}