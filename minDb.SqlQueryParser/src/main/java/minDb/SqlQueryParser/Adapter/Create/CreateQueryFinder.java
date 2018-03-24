package minDb.SqlQueryParser.Adapter.Create;

import java.util.ArrayList;
import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnMetaInfo;
import minDb.Core.MetaInfo.ColumnType;
import minDb.Core.MetaInfo.ColumnType.Type;
import minDb.Extensions.EnumExtensions;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;

/**
 * CreateQueryFinder
 */
public class CreateQueryFinder implements ICreateQueryAdapter {

    public List<ColumnMetaInfo> getCreateTableColumns(CreateTable createQuery) throws ValidationException
    {
        List<ColumnMetaInfo> createColumns = new ArrayList<ColumnMetaInfo>();

        if(createQuery.getColumnDefinitions() == null || createQuery.getColumnDefinitions().isEmpty())
        {
            throw new ValidationException("Empty column definitions while create table query.");
        }

        for(ColumnDefinition columnDefinition : createQuery.getColumnDefinitions())
        {
            ColumnType.Type type = EnumExtensions.parse(ColumnType.Type.class, columnDefinition.getColDataType().getDataType());
            if(type == null)
            {
                throw new ValidationException("Unsupported column type.");
            }

            int length = -1;
            if(type == Type.varchar)
            {
                List<String> arguments = columnDefinition.getColDataType().getArgumentsStringList();
                if(arguments.isEmpty())
                {
                    throw new ValidationException("Arguments is empty for type varchar");
                }

                length = Integer.valueOf(arguments.get(0));

                if(length < 0 || length > 256)
                {
                    throw new ValidationException("Length of varchar column is not valid.");
                }
            }

            createColumns.add(new ColumnMetaInfo(new ColumnType(type, length), columnDefinition.getColumnName()));
        }

        return createColumns;
    }
}