package minDb.DataProvider.Data.IO;

import minDb.Core.Components.Data.ITypeSizeProvider;
import minDb.Core.MetaInfo.TableMetaInfo;

/**
 * BaseIOProcessor
 */
public class BaseIOProcessor {

    private ITypeSizeProvider _typeSizeProvider;

	public BaseIOProcessor(ITypeSizeProvider typeSizeProvider) {
        super();
        _typeSizeProvider = typeSizeProvider;
    }

    protected int[] getOffsetTable(TableMetaInfo tableInfo) throws minDb.Core.Exceptions.ValidationException {
        int cols = tableInfo.get_columnsInfo().size();
        int[] offsetTable = new int[cols + 1];
        int index = 0;
        offsetTable[index++] =  tableInfo.get_columnsInfo().size() / Byte.SIZE + 1;

        for (int i = 0; i < cols; ++i, ++index) {
            int size = _typeSizeProvider.getBytesSize(tableInfo.get_columnsInfo().get(i).get_columnType());
            offsetTable[index] = offsetTable[i] + size;
        }
        return offsetTable;
    }    
}