package minDb.Core.Data;

import java.util.List;

/**
 * IDataTable
 */
public interface IDataTable {
    IDataRow get(int i);
    List<String> getHeader();
}