package minDb.DataProvider.Data.Models;

import java.util.List;

/**
 * DataRow
 */
public class DataRow implements minDb.Core.Components.Data.IDataRow {
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