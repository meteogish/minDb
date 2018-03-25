package minDb.Extensions;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnType;

/**
 * ColumnTypeSizeExtension
 */
public class ColumnTypeExtension {

    public static int getSize(ColumnType columnType) throws ValidationException
    {
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