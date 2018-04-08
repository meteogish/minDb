package minDb.Core.Components;

import java.io.Closeable;

import minDb.Core.Components.Data.IDataTable;
import minDb.Core.Exceptions.ValidationException;

/**
 * IDbConnection
 */
public interface IDbConnection extends Closeable {
    public enum ConnectionStatus
    {
        Open,
        Closed,
        New
    }

    ConnectionStatus get_Status() throws ValidationException;
    void connect(String dbFolderPath) throws ValidationException;
    IDataTable executeQuery(String strQuery) throws ValidationException;
}


