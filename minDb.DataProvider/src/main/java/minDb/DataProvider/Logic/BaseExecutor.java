package minDb.DataProvider.Logic;

import java.util.ArrayList;
import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Column;

/**
 * BaseExecutor
 */
public class BaseExecutor {
    protected final List<Integer> getIndexesOfColumns(TableMetaInfo tableInfo, List<Column> selectedColumns) throws ValidationException {
        List<Integer> indexes = new ArrayList<Integer>();
        Boolean columnExists;
        for (Column c : selectedColumns) {
            columnExists = false;
            for (int i = 0; i < tableInfo.get_columnsInfo().size(); i++) {
                if (c.get_name().equalsIgnoreCase(tableInfo.get_columnsInfo().get(i).get_name())) {
                    indexes.add(i);
                    columnExists = true;
                    break;
                }
            }

            if(!columnExists)
            {
                throw new ValidationException("The column " + c.get_name() + " not exists in table " + c.get_table().get_name());
            }
        }
        return indexes;
    }
    
}