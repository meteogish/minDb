package minDb.DataProvider.HelpDto;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.QueryModels.Conditions.ColumnCondition.Compare;

/**
 * JoinRawDataCondition
 */
public class JoinRawDataCondition {
    private Integer _leftColumnIndex;
    private Integer _joinTableColumnIndex;
    private Compare _compare;

    /**
     * @return the leftColumnIndex
     */
    public Integer getLeftColumnIndex() {
        return _leftColumnIndex;
    }

    /**
    * @return the _joinTableColumnIndex
    */
    public Integer get_joinTableColumnIndex() {
        return _joinTableColumnIndex;
    }

    /**
     * @return the _compare
     */
    public Compare get_compare() {
        return _compare;
    }

    public JoinRawDataCondition(int dataColumnIndex, int jointableColumnIndex, Compare compare)
            throws ValidationException {
        if (dataColumnIndex < 0) {
            throw new ValidationException("Invalid column index in join condition.");
        }

        if (jointableColumnIndex < 0) {
            throw new ValidationException("Invalid column index in join condition.");
        }

        if(compare == null)
        {
            throw new ValidationException("Compare is null in join condition.");            
        }

        _leftColumnIndex = dataColumnIndex;
        _joinTableColumnIndex = jointableColumnIndex;
        _compare = compare;
    }
}