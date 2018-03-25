package minDb.Core.Data;

/**
 * IDataRow
 */
public interface IDataRow {
    String getString(int columnIndex);
    String getString(String columnName);
    
    Double getDouble(int columnIndex);
    Double getDouble(String columnName);

    Integer getInt(int columnIndex);
    Integer getInt(String columnName);

    Object getObject(int columnIndex);
    Object gObject(String columnName);

}