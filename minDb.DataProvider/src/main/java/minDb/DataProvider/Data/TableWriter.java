package minDb.DataProvider.Data;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.List;

import minDb.Core.Data.IRawTableWriter;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnType;
import minDb.Core.MetaInfo.ColumnType.Type;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Extensions.ColumnTypeExtension;

/**
 * TableWriter
 */
public class TableWriter implements IRawTableWriter {
 
    public void writeTo(TableMetaInfo tableInfo, String dbFolder, List<Object> insertValues)
            throws ValidationException {
        File tableFile = findFile(tableInfo.get_tableName(), dbFolder);
        
        BufferedOutputStream file; 
        try {
            file = new BufferedOutputStream(new FileOutputStream(tableFile, true));
        } catch (FileNotFoundException e1) {
            throw new ValidationException("Table file not found");
        }

        try {
            BitSet bs = getRowNullsInfo(tableInfo, insertValues);
            byte[] nullsInfo = bs.toByteArray();
            
            int[] offsetTable = getOffsetTable(tableInfo, nullsInfo.length);
            
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
                    Integer integer = ((Long)value).intValue();
                    buffer.putInt(integer);
                } else {
                    throw new ValidationException("Unsupported");
                }
            }
            file.write(row);
            file.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private File findFile(String tableName, String dbFolder) throws ValidationException {
        Path fullFilePath = Paths.get(dbFolder, tableName + ".tb");
        File tableFile = new File(fullFilePath.toUri());

        if (!tableFile.exists()) {
            throw new ValidationException("Table file not exists.");
        }
        return tableFile;
    }

    private int[] getOffsetTable(TableMetaInfo tableInfo, int nullInfoOffset) throws ValidationException {
        int cols = tableInfo.get_columnsInfo().size();
        int[] offsetTable = new int[cols + 1];

        offsetTable[0] = nullInfoOffset;

        for (int i = 1; i < cols + 1; i++) {
            int index = i - 1;
            int size = ColumnTypeExtension.getSize(tableInfo.get_columnsInfo().get(index).get_columnType());
            offsetTable[i] = offsetTable[index] + size;
        }

        return offsetTable;
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