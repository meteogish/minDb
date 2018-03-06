package minDb.Core.QueryModels;

import java.util.ArrayList;
import java.util.List;

import minDb.Core.Exceptions.ValidationException;

/**
 * Join
 */
public class Join {
    private Table _table;
    private List<ColumnCondition> _columns = new ArrayList<ColumnCondition>();

    /**
     * @return the _table
     */
    public Table get_table() {
        return _table;
    }

    public Join(Table table) {
        _table = table;
    }

    public Join on(String leftColumn, Table leftTable, ValueCompare compare, String rightColumn, Table rightTable) throws ValidationException {
        _columns.add(new JoinColumnCondition(leftTable, leftColumn, compare, rightTable, rightColumn));
        return this;
    }

    public <T> Join on(String leftColumn, Table leftTable, ValueCompare compare, T value) throws ValidationException {
        _columns.add(new ValueColumnCondition<T>(leftTable, leftColumn, compare, value));
        return this;
    }

    public void on(ColumnCondition condition) throws ValidationException
    {
        if(condition == null)
        {
            throw new ValidationException("Condition parameter is null");
        }
        _columns.add(condition);
    }

	/**
	 * @return the _condition
	 */
	public List<ColumnCondition> get_conditions() {
		return _columns;
    }
    
    public static Join table(Table t)
    {
        return new Join(t);
    }
}