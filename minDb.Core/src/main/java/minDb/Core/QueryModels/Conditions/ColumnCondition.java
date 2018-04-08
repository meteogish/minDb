package minDb.Core.QueryModels.Conditions;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Column;
import minDb.Core.QueryModels.Table;

/**
 * ColumnConfition
 */
public abstract class ColumnCondition {
    public enum Compare {
        EQUALS("="), NOT_EQUALS("<>"), GREATER(">"), LESS("<");

        private final String _type;

        Compare(String type) {
            _type = type;
        }

        @Override
        public String toString() {
            return _type;
        }
    }

    protected Column _leftColumn;
    protected Compare _compare;

    /**
     * @return the _compare
     */
    public Compare get_compare() {
        return _compare;
    }

    /**
     * @return the _leftColumn
     */
    public Column get_leftColumn() {
        return _leftColumn;
    }

    public ColumnCondition(Table table, String column, Compare compare) throws ValidationException {
        if (compare == null) {
            throw new ValidationException("Compare parameter is null.");
        }

        _leftColumn = new Column(table, column);
        _compare = compare;
    }

    public ColumnCondition(Column leftColumn, Compare compare) throws ValidationException {
        if (leftColumn == null) {
            throw new ValidationException("Column parameter is null.");
        }

        _leftColumn = leftColumn;
        _compare = compare;
    }

    protected Boolean compareValues(Object left, Object right) throws ValidationException {
        boolean leftIsNull = left == null;
        boolean rightIsNull = right == null;

        if (_compare == Compare.EQUALS) {
            if (leftIsNull) {
                if (rightIsNull) {
                    return true;
                }
                return false;
            } else {
                if(left instanceof Number && right instanceof Number)
                {
                    return ((Number)left).doubleValue() == ((Number)right).doubleValue();
                }
                return left.equals(right);
            }
        } else if (_compare == Compare.NOT_EQUALS) {
            if (leftIsNull) {
                if (rightIsNull) {
                    return false;
                }
                return true;
            } else {
                return left.equals(right) == false;
            }
        } else {
            if (!leftIsNull && left instanceof Comparable) {
                if (!rightIsNull && right instanceof Comparable) {
                    if (_compare == Compare.LESS) {
                        return ((Comparable<Object>) left).compareTo(((Number)right).doubleValue()) < 0;
                    } else if (_compare == Compare.GREATER) {
                        return ((Comparable<Object>) left).compareTo(((Number)right).doubleValue()) > 0;
                    } else {
                        throw new ValidationException("Compare not supported yet");
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }
}