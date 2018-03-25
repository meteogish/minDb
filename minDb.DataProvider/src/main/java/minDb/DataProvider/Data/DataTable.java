package minDb.DataProvider.Data;

import java.util.List;

import minDb.Core.Data.IDataRow;
import minDb.Core.Data.IDataTable;

/**
 * DataTable
 */
public class DataTable implements IDataTable {

    private List<IDataRow> _rows;
	private List<String> _header;

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
}