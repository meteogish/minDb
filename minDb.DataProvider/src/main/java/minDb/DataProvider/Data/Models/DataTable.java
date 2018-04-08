package minDb.DataProvider.Data.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import minDb.Core.Components.Data.IDataRow;
import minDb.Core.Components.Data.IDataTable;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Column;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.Conditions.ColumnCondition.Compare;
import minDb.Core.QueryModels.Conditions.ICondition;
import minDb.Core.QueryModels.Conditions.JoinColumnCondition;
import minDb.DataProvider.HelpDto.JoinRawDataCondition;

/**
 * DataTable
 */
public class DataTable implements IDataTable {

	private List<List<Object>> _rows;
	private List<Column> _header;
	private List<Integer> _select;

	public DataTable(List<Column> header, List<List<Object>> rows) {
		_header = header;
		_rows = rows;
	}

	public void print() {
		StringBuilder header = new StringBuilder("| ");
		for (Integer columnIndex : _select) {
			header.append(_header.get(columnIndex).getNameWithAlias() + " | ");
		}

		System.out.println(header);
		for (List<Object> row : _rows) {
			StringBuilder builder = new StringBuilder("| ");
			for (Integer i : _select) {
				Object o = row.get(i);
				if (o == null) {
					builder.append(" null |");
				} else {
					builder.append(" " + o.toString() + " |");
				}
			}
			System.out.println(builder);
		}
	}

	public void join(DataTable joinData, List<JoinColumnCondition> conditions) throws ValidationException {
		List<JoinRawDataCondition> indexes = new ArrayList<JoinRawDataCondition>(conditions.size());

		for (JoinColumnCondition condition : conditions) {
			Integer joinTableDataIndex = joinData.getIndexOfColumn(condition.get_leftColumn());
			Integer dataIndex = this.getIndexOfColumn(condition.get_rightColumn());
			indexes.add(new JoinRawDataCondition(dataIndex, joinTableDataIndex, condition.get_compare()));
		}
		List<List<Object>> newData = new ArrayList<List<Object>>();

		for (int i = 0; i < _rows.size(); ++i) {
			for (int j = 0; j < joinData._rows.size(); j++) {
				if (canJoin(_rows.get(i), joinData._rows.get(j), indexes)) {
					List<Object> newRow = new ArrayList<Object>(_rows.get(i));
					newRow.addAll(joinData._rows.get(j));
					newData.add(newRow);
				}
			}
		}
		_rows = newData;
		_header.addAll(joinData._header);
	}

	public List<List<Object>> getRows()
	{
		return _rows;
	}

	public IDataRow get(Integer i) {
		return new DataRow(_rows.get(i));
	}

	public Integer getIndexOfColumn(Column column) {
		return _header.indexOf(column);
	}

	public void filter(ICondition condition) throws ValidationException {
		if (condition == null) {
			return;
		}

		for (int i = 0; i < _rows.size(); ++i) {
			if (!condition.apply(new DataRow(_rows.get(i)), this::getIndexOfColumn)) {
				_rows.remove(i);
			}
		}
	}

	private boolean canJoin(List<Object> dataTableRow, List<Object> joinTableRow,
			List<JoinRawDataCondition> conditions) {
		for (JoinRawDataCondition condition : conditions) {
			Object dataValue = dataTableRow.get(condition.getLeftColumnIndex());
			Object joinValue = joinTableRow.get(condition.get_joinTableColumnIndex());
			if (condition.get_compare() == Compare.EQUALS) {
				if (!dataValue.equals(joinValue)) {
					return false;
				}
			}
		}
		return true;
	}

	public void select(List<SelectColumn> selectColumns) throws ValidationException {
		try {
			if (selectColumns.isEmpty()) {
				_select = IntStream.range(0, _header.size()).boxed().collect(Collectors.toList());
			} else {
				_select = selectColumns.stream().map(sc -> getIndexOfColumn(sc)).collect(Collectors.toList());
			}
		} catch (Exception ex) {
			throw new ValidationException("Error during select phase: " + ex.getMessage());
		}
	}

}