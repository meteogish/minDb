package minDb.Core.Components.Data;

/**
 * IDataRow
 */
public interface IDataRow {
    String getString(int columnIndex);
    
    Double getDouble(int columnIndex);

    Integer getInt(int columnIndex);

    Object getObject(int columnIndex);

}