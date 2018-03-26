package minDb.Core.QueryModels;

import java.util.ArrayList;
import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Conditions.JoinColumnCondition;
import minDb.Core.QueryModels.Conditions.ColumnCondition.Compare;

/**
 * Join
 */
public class Join {
    private Table _table;
    private List<JoinColumnCondition> _columns = new ArrayList<JoinColumnCondition>();

    /**
     * @return the _table
     */
    public Table get_table() {
        return _table;
    }

    public Join(Table table) {
        _table = table;
    }

    public Join on(String leftColumn, Table leftTable, Compare compare, String rightColumn, Table rightTable) throws ValidationException {
        _columns.add(new JoinColumnCondition(leftTable, leftColumn, compare, rightTable, rightColumn));
        return this;
    }

    // public <T> Join on(String leftColumn, Table leftTable, Compare compare, T value) throws ValidationException {
    //     _columns.add(new ValueColumnCondition<T>(leftTable, leftColumn, compare, value));
    //     return this;
    // }

    public void on(JoinColumnCondition condition) throws ValidationException
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
	public List<JoinColumnCondition> get_conditions() {
		return _columns;
    }
    
    public static Join table(Table t)
    {
        return new Join(t);
    }
}