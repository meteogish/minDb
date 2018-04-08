package minDb.Connection;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import minDb.Core.Components.IDbConnection;
import minDb.Core.Components.IInsertQueryExecutor;
import minDb.Core.Components.IMetaInfoRepository;
import minDb.Core.Components.IQueryParser;
import minDb.Core.Components.ISelectQueryExecutor;
import minDb.Core.Components.IUpdateQueryExecutor;
import minDb.Core.Components.Data.IDataTable;
import minDb.Core.Components.Data.ITableFileProvider;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Queries.Query;
import minDb.Core.QueryModels.Queries.Query.QueryType;
import minDb.Extensions.StringExtenstions;

/**
 * SqlConnection
 */
public class SqlConnection implements IDbConnection {
    private ConnectionStatus _status;
    private IQueryParser _queryParser;
    private IMetaInfoRepository _metaInfoRepository;
    private ISelectQueryExecutor _selectQueryExecutor;
    private IInsertQueryExecutor _insertQueryExecutor;
    private ITableFileProvider _tableFileProvider;
    private IUpdateQueryExecutor _updateExecutor;

    private String _dbFolder;
    private String _dbName;

    private File _dbFile;
    DatabaseMetaInfo _dbInfo;

    public SqlConnection(IQueryParser queryParser, IMetaInfoRepository metaInfoRepository,
            ISelectQueryExecutor selectQueryExecutor, IInsertQueryExecutor insertQueryExecutor,
            ITableFileProvider tableFileProvider, IUpdateQueryExecutor updateExecutor) {
        _queryParser = queryParser;
        _metaInfoRepository = metaInfoRepository;
        _selectQueryExecutor = selectQueryExecutor;
        _insertQueryExecutor = insertQueryExecutor;
        _tableFileProvider = tableFileProvider;
        _updateExecutor = updateExecutor;
        _status = ConnectionStatus.Closed;
    }

    public ConnectionStatus get_Status() throws ValidationException {
        return _status;
    }

    public void connect(String dbFolderPath) throws ValidationException {
        File dbFile = new File(dbFolderPath);
        if (dbFile.isDirectory()) {
            File[] files = dbFile.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith(".db");
                }
            });

            if (files.length == 0) {
                throw new ValidationException("There is no database info files in the folder.");
            }

            if (files.length > 1) {
                throw new ValidationException("Multiple database info files in the folder.");
            }
            dbFile = files[0];
        }
        _dbInfo = _metaInfoRepository.getDatabaseMetaInfo(dbFile);
        _dbFolder = dbFile.getParent();
        _status = ConnectionStatus.Open;
    }

    public IDataTable executeQuery(String strQuery) throws ValidationException {
        if (_status == ConnectionStatus.Closed) {
            if (isUseDatabaseQuery(strQuery)) {
                String dbPath = useDatabaseQuery(strQuery);
                connect(dbPath);
                return new ConnectionFeedback("Connected to db");
            } else if (isCreateDatabaseQuery(strQuery)) {
                createDatabase(strQuery);
                return new ConnectionFeedback("Created db " + _dbName);
            } else {
                throw new ValidationException("Connection not open. Please input 'use' or 'create database' query.");
            }
        }

        Query query = _queryParser.parse(strQuery);
        if (query.get_type() == QueryType.Select) {
            return _selectQueryExecutor.execute(query.get_select(), _dbInfo, _dbFolder);
        } else if (query.get_type() == QueryType.Update) {
            _updateExecutor.execute(query.get_update(), _dbInfo, _dbFolder);
            return new ConnectionFeedback("Succesfull update.");            
        } else if (query.get_type() == QueryType.Insert) {
            _insertQueryExecutor.execute(query.get_insert(), _dbInfo, _dbFolder);
            return new ConnectionFeedback("Succesfull insert.");
        } else if (query.get_type() == QueryType.CreateTable) {
            _dbInfo.createtable(query.get_createTableInfo());
            _status = ConnectionStatus.New;
            return new ConnectionFeedback("Table created succesfully.");
        } else if (query.get_type() == QueryType.DropTable) {
            TableMetaInfo table = _dbInfo.getTableMetaInfo(query.get_dropTable().get_name());
            _tableFileProvider.delete(table.get_tableName(), _dbFolder);
            _dbInfo.dropTable(table);
            _status = ConnectionStatus.New;
            return new ConnectionFeedback("Table droped succesfully.");
        }

        throw new ValidationException("Unsupported query behavior.");
    }

    private void createDatabase(String query) throws ValidationException {
        String dbName = query.substring("create database ".length());
        if (StringExtenstions.IsNullOrEmpty(dbName)) {
            throw new ValidationException("Invalid name for new db");
        }
        dbName = dbName.trim();
        File dbFolder = new File("./");

        _dbFolder = dbFolder.getAbsolutePath();
        _dbName = dbName;
        _dbInfo = new DatabaseMetaInfo();
        _status = ConnectionStatus.New;
    }

    private boolean isCreateDatabaseQuery(String query) {
        return query.toLowerCase().startsWith("create database ");
    }

    private Boolean isUseDatabaseQuery(String query) {
        return query.toLowerCase().startsWith("use");
    }

    private String useDatabaseQuery(String query) {
        return query.substring("use ".length());
    }

    public void close() throws IOException {
        if (_status == ConnectionStatus.New) {
            try {
                if (_dbFile != null) {
                    _metaInfoRepository.saveDatabaseMetaInfo(_dbInfo, _dbFile);
                } else {
                    _metaInfoRepository.saveDatabaseMetaInfo(_dbInfo, _dbFolder, _dbName);
                }
            } catch (ValidationException e) {
                throw new IOException(e.getMessage());
            }
        }
        _status = ConnectionStatus.Closed;
        _dbFile = null;
        _dbInfo = null;
        _dbFolder = null;
    }
}