package minDb.DataProvider.Data;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import minDb.Core.Data.IDataRow;
import minDb.Core.Data.IDataTable;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.SelectColumn;

/**
 * DataTable
 */
public class DataTable implements IDataTable {

	private List<IDataRow> _rows;
	private List<String> _header;

	private List<Integer> _selectedColumns;

	public DataTable(List<String> header, List<IDataRow> rows) {
        _header = header;
		_rows = rows;		
    }

	@Override
	public IDataRow get(int i) {
		return _rows.get(i);
	}

	@Override
	public List<String> getHeader() {
		return _header;
	}

	/**
	 * @return the _rows
	 */
	public List<IDataRow> get_rows() {
		return _rows;
	}

	@Override
	public void select(List<SelectColumn> selectColumns) throws ValidationException {
		if (selectColumns != null && !selectColumns.isEmpty()) {
					
			int[] indexes =  IntStream.range(0, _header.size())
			.filter(i ->
			{
				int l = i;
				return selectColumns.stream().filter(p -> p.get_name().equalsIgnoreCase(_header.get(l))).findFirst().isPresent();
			})
			.toArray();
			
			_selectedColumns = Arrays.stream(indexes).boxed().collect(Collectors.toList());
		}
	}

	@Override
	public void print() {
		String header = _selectedColumns.stream().map(i -> _header.get(i)).reduce("|", String::concat);
		System.out.println(header);
		for (IDataRow row : _rows) {
			StringBuilder builder = new StringBuilder();
			for(int i : _selectedColumns)
			{
				Object o = row.getObject(i);
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
}