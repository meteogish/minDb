package minDb.DataProvider.Data;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import minDb.Core.Data.IDataRow;
import minDb.Core.Data.IDataTable;
import minDb.Core.Data.IRawTableReader;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnType;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.MetaInfo.ColumnType.Type;
import minDb.Extensions.ColumnTypeExtension;

/**
 * TableReader
 */
public class TableReader implements IRawTableReader {

    public IDataTable readFrom(TableMetaInfo tableInfo, String dbFolder) throws ValidationException {
        File tableFile = findFile(tableInfo.get_tableName(), dbFolder);
        
        BufferedInputStream file;
        try {
            file = new BufferedInputStream(new FileInputStream(tableFile));
        } catch (FileNotFoundException e) {
            throw new ValidationException("Table file not found");
        }
        
        List<IDataRow> rows = new ArrayList<IDataRow>();
        List<String> header = tableInfo.get_columnsInfo().stream().map(p -> p.get_name()).collect(Collectors.toList());
        try {
            int[] offsetTable = getOffsetTable(tableInfo, tableInfo.get_columnsInfo().size() / Byte.SIZE + 1);
            int rowSize = offsetTable[offsetTable.length - 1];
            while (file.available() > 0) {
                byte[] row = new byte[rowSize];                
                file.read(row);
                ByteBuffer buffer = ByteBuffer.wrap(row);
                
                byte[] nullsInfo = new byte[offsetTable[0]];
                buffer.get(nullsInfo);
                BitSet bs = BitSet.valueOf(nullsInfo);
                
                List<Object> values = new ArrayList<Object>(tableInfo.get_columnsInfo().size());

                for (int i = 0; i < tableInfo.get_columnsInfo().size(); ++i) {
                    ColumnType columnType = tableInfo.get_columnsInfo().get(i).get_columnType();

                    if(bs.get(i))
                    {
                        values.add(null);
                        continue;
                    }

                    int off = offsetTable[i];
                    buffer.position(off);
                    if (columnType.get_type() == Type.DOUBLE) {
                        Double value = buffer.getDouble();
                        values.add((Object) value);
                    } else if (columnType.get_type() == Type.VARCHAR) {
                        byte length = buffer.get();
                        String value = new String(row, off + 1, length, StandardCharsets.UTF_8);
                        values.add(value);
                    } else if (columnType.get_type() == Type.INT) {
                        Integer value = buffer.getInt();
                        values.add((Object) value);
                    } else {
                        throw new ValidationException("Unsupported");
                    }
                }
                rows.add(new DataRow(values));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return new DataTable(header, rows);
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
}