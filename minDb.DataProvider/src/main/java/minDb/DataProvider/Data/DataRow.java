package minDb.DataProvider.Data;

import java.util.List;
import java.util.stream.IntStream;

import minDb.Core.Data.IDataRow;

/**
 * DataRow
 */
public class DataRow implements IDataRow {
    private List<Object> _values;
	private List<String> _header;
    
    public DataRow(List<String> header, List<Object> values) {
        _values = values;
        _header = header;
    }
    
	public String getString(int columnIndex) {
        return getObject(columnIndex).toString();
	}
    
	public String getString(String columnName) {
        return getString(getColumnIndex(columnName));
	}
    
	public Double getDouble(int columnIndex) {
        return (Double)getObject(columnIndex);
	}
    
	public Double getDouble(String columnName) {
        return getDouble(getColumnIndex(columnName));
	}
    
	@Override
	public Integer getInt(int columnIndex) {
        return (Integer)getObject(columnIndex);
	}
    
	@Override
	public Integer getInt(String columnName) {
        return getInt(getColumnIndex(columnName));
	}
    
	@Override
	public Object getObject(int columnIndex) {
        return _values.get(columnIndex);
	}
    
	@Override
	public Object gObject(String columnName) {
        return getObject(getColumnIndex(columnName));
    }
       
    private int getColumnIndex(String columnName)
    {
        return IntStream.range(0, _header.size())
                        .filter(i -> _header.get(i)
                        .equalsIgnoreCase(columnName))
                        .findFirst()
                        .getAsInt();
    }
}