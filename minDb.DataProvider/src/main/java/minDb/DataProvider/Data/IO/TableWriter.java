package minDb.DataProvider.Data.IO;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.List;

import minDb.Core.Components.Data.IRawTableWriter;
import minDb.Core.Components.Data.ITypeSizeProvider;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnType;
import minDb.Core.MetaInfo.ColumnType.Type;
import minDb.Core.MetaInfo.TableMetaInfo;

/**
 * TableWriter
 */
public class TableWriter extends BaseIOProcessor implements IRawTableWriter {
 
    public TableWriter(ITypeSizeProvider typeSizeProvider) {
        super(typeSizeProvider);
    }

    public void writeTo(TableMetaInfo tableInfo, File tableFile, List<Object> insertValues) throws ValidationException {
        BufferedOutputStream file; 
        try {
            file = new BufferedOutputStream(new FileOutputStream(tableFile, true));
        } catch (FileNotFoundException e1) {
            throw new ValidationException("Table file not found");
        }

        try {
            BitSet bs = getRowNullsInfo(tableInfo, insertValues);
            byte[] nullsInfo = bs.toByteArray();
            
            int[] offsetTable = getOffsetTable(tableInfo);
            
            int rowSize = offsetTable[offsetTable.length - 1];
            
            byte[] row = new byte[rowSize];
            ByteBuffer buffer = ByteBuffer.wrap(row);
            buffer.put(nullsInfo);
            
            for (int i = 0; i < insertValues.size(); ++i) {
                Object value = insertValues.get(i);

                if (value == null) {
                    continue;
                }
                ColumnType columnType = tableInfo.get_columnsInfo().get(i).get_columnType();

                int off = offsetTable[i];
                buffer.position(off);
                if (columnType.get_type() == Type.DOUBLE) {
                    Double d = (Double) value;
                    buffer.putDouble(d);
                } else if (columnType.get_type() == Type.VARCHAR) {
                    String str = (String) value;
                    byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
                    byte length = (byte)bytes.length;
                    buffer.put(length);         
                    buffer.put(bytes);
                } else if (columnType.get_type() == Type.INT) {
                    Integer integer = ((Number)value).intValue();
                    buffer.putInt(integer);
                } else {
                    throw new ValidationException("Unsupported");
                }
            }
            file.write(row);
            file.flush();
        } catch (IOException e) {
            throw new ValidationException("Error during read table. " + e.getMessage());
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                throw new ValidationException("Error during read table. " + e.getMessage());
            }
        }
    }

    private BitSet getRowNullsInfo(TableMetaInfo tableInfo, List<Object> values) {
        BitSet bs = new BitSet(values.size());

        for (int i = 0; i < values.size(); ++i) {
            if(values.get(i) == null)
            {
                bs.set(i);
            }
        }
        return bs;
    }
}