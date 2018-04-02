package minDb.DataProvider.Data.Models;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import minDb.Core.Components.Data.IDataRow;
import minDb.Core.Components.Data.IDataTable;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Column;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.Conditions.JoinColumnCondition;

/**
 * DataTable
 */
public class DataTable implements IDataTable {

	private List<List<Object>> _rows;
	private List<Column> _header;

	private List<Integer> _selectedColumns;

	public DataTable(List<Column> header, List<List<Object>> rows) {
        _header = header;
		_rows = rows;		
    }

	@Override
	public IDataRow get(int i) {
		return new DataRow(_rows.get(i));
	}

	@Override
	public List<String> getHeader() {
		return null;
	}

	/**
	 * @return the _rows
	 */
	public List<DataRow> get_rows() {
		return null;
	}

	@Override
	public void select(List<SelectColumn> selectColumns) throws ValidationException {
		if (selectColumns != null && !selectColumns.isEmpty()) {
					
			int[] indexes =  IntStream.range(0, _header.size())
			.filter(i ->
			{
				int l = i;
				return true;//selectColumns.stream().filter(p -> p.get_name().equalsIgnoreCase(_header.get(l))).findFirst().isPresent();
			})
			.toArray();
			
			_selectedColumns = Arrays.stream(indexes).boxed().collect(Collectors.toList());
		}
	}

	public Integer getIndex(String columnName)
	{
		for (int i = 0; i < _header.size(); i++) {
			if(_header.get(i).get_name().equalsIgnoreCase(columnName))
			{
				return i;
			}
		}
		return null;
	}

	@Override
	public void print() {
		String header = _selectedColumns.stream().map(i -> _header.get(i).get_name()).reduce("|", String::concat);
		System.out.println(header);
		for (List<Object> row : _rows) {
			StringBuilder builder = new StringBuilder();
			for(int i : _selectedColumns)
			{
				Object o = row.get(i);
				if(o == null)
				{
					builder.append(" | null");
				} 
				else
				{
					builder.append(" | " + o.toString());
				}
			}
			System.out.println(builder);	
		}
	}

	public void join(DataTable joinData, List<JoinColumnCondition> conditions) {
		for(int i = 0; i < _rows.size(); ++i)
		{
			for (int j = 0; j < joinData._rows.size(); j++) {
				// _rows.get(i).join(joinData._rows.get(j));				
			}
		}
	}

	@Override
	public Integer getColumnsCount() {
		return _selectedColumns.size();
	}


}