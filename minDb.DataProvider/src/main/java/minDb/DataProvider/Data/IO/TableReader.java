package minDb.DataProvider.Data.IO;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import minDb.Core.Components.Data.IRawTableReader;
import minDb.Core.Components.Data.ITypeSizeProvider;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnType;
import minDb.Core.MetaInfo.ColumnType.Type;
import minDb.Core.MetaInfo.TableMetaInfo;

/**
 * TableReader
 */
public class TableReader extends BaseIOProcessor implements IRawTableReader {
    public TableReader(ITypeSizeProvider typeSizeProvider) {
        super(typeSizeProvider);
    }

    public List<List<Object>> readFrom(TableMetaInfo tableInfo, File tableFile) throws ValidationException {
        return readFrom(tableInfo, tableFile,
                IntStream.range(0, tableInfo.get_columnsInfo().size()).boxed().collect(Collectors.toList()));
    }

    public List<List<Object>> readFrom(TableMetaInfo tableInfo, File tableFile, List<Integer> selectColumnIndexes)
            throws ValidationException {
        BufferedInputStream file;
        try {
            file = new BufferedInputStream(new FileInputStream(tableFile));
        } catch (FileNotFoundException e) {
            throw new ValidationException("Table file not found");
        }

        // List<String> header = tableInfo.get_columnsInfo().stream().map(p -> p.get_name()).collect(Collectors.toList());

        List<List<Object>> rows = new ArrayList<List<Object>>();
        try {
            int[] offsetTable = getOffsetTable(tableInfo);

            int rowSize = offsetTable[offsetTable.length - 1];

            while (file.available() > 0) {
                byte[] row = new byte[rowSize];
                file.read(row);
                ByteBuffer buffer = ByteBuffer.wrap(row);

                byte[] nullsInfo = new byte[offsetTable[0]];
                buffer.get(nullsInfo);
                BitSet bs = BitSet.valueOf(nullsInfo);

                List<Object> values = new ArrayList<Object>(tableInfo.get_columnsInfo().size());

                for (int i = 0; i < selectColumnIndexes.size(); ++i) {
                    int columnIndex = selectColumnIndexes.get(i);
                    ColumnType columnType = tableInfo.get_columnsInfo().get(columnIndex).get_columnType();

                    if (bs.get(columnIndex)) {
                        values.add(null);
                        continue;
                    }

                    int off = offsetTable[columnIndex];
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
                rows.add(values);
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
        return rows;
    }
}