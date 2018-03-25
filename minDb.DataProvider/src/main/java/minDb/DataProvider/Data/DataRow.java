package minDb.DataProvider.Data;

import java.util.List;

import minDb.Core.Data.IDataRow;

/**
 * DataRow
 */
public class DataRow implements IDataRow {
    private List<Object> _values;
    
    public DataRow(List<Object> values) {
        _values = values;
    }
    
	public String getString(int columnIndex) {
        return getObject(columnIndex).toString();
	}
        
	public Double getDouble(int columnIndex) {
        return (Double)getObject(columnIndex);
	}
    
	@Override
	public Integer getInt(int columnIndex) {
        return (Integer)getObject(columnIndex);
	}
       
	@Override
	public Object getObject(int columnIndex) {
        return _values.get(columnIndex);
	}
}