package minDb.DataProvider.Data;

import minDb.Core.Components.Data.ITypeSizeProvider;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnType;

/**
 * TypeSizeProvider
 */
public class TypeSizeProvider implements ITypeSizeProvider {

	@Override
	public int getBytesSize(ColumnType columnType) throws ValidationException {
		int typeSize = -1;
        switch (columnType.get_type()) {
            case DOUBLE:
                typeSize = Double.SIZE / Byte.SIZE;
                break;
            case VARCHAR:
                typeSize = columnType.get_length() + 1;
                break;
            case INT:
                typeSize = Integer.SIZE / Byte.SIZE;
                break;
            default:
                throw new ValidationException("Not supported type");
        }
        return typeSize;
	}

    
}