package minDb.Core.QueryModels;

import java.util.ArrayList;
import java.util.List;

/**
 * Join
 */
public class Join {
    private Table _table;
    private List<ColumnCondition> _conditions = new ArrayList<ColumnCondition>();

    /**
     * @return the _conditions
     */
    public List<ColumnCondition> get_conditions() {
        return _conditions;
    }

    /**
     * @return the _table
     */
    public Table get_table() {
        return _table;
    }

    public Join(Table table) {
        _table = table;
    }

    public void On(Table leftTable, String leftColumn, Compare compare, Table rightTable, String rightColumn) {

    }
}