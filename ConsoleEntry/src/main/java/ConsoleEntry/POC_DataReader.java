package ConsoleEntry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;

/**
 * POC_DataReader
 */
public class POC_DataReader {
    private TableMetaInfo _tableInfo;
    private String _dbFolder;
    private File _tableFile;

    public POC_DataReader(TableMetaInfo tableInfo, String dbFolder) throws ValidationException {
        _tableInfo = tableInfo;
        _dbFolder = dbFolder;

        Path fullFilePath = Paths.get(_dbFolder, tableInfo.get_tableName() + ".tb");
        _tableFile = new File(fullFilePath.toUri());

        if (!_tableFile.exists()) {
            throw new ValidationException("Table file not exists.");
        }
    }

    public void Write(String[] insertValues) throws ValidationException {
        if (insertValues.length != _tableInfo.get_columnsInfo().size()) {
            throw new ValidationException("Size of insert values != columns to insert.");
        }

        try {
            DataOutputStream writer = new DataOutputStream(new FileOutputStream(_tableFile, true));

            for (int i = 0; i < insertValues.length; ++i) {
                ColumnMetaInfo column = _tableInfo.get_columnsInfo().get(i);

                switch (column.get_columnType().get_type()) {
                case decimal:
                    Double d = Double.parseDouble(insertValues[i]);
                    writer.writeDouble(d);
                    break;
                case varchar:
                    writer.writeUTF(insertValues[i]);
                    break;
                case integer:
                    Integer integer = Integer.parseInt(insertValues[i]);
                    writer.writeInt(integer);
                    break;
                default:
                    throw new ValidationException("Unsupported");
                }
            }
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<Object[]> read() throws ValidationException{
        List<Object[]> objects = new ArrayList<Object[]>();

        try {
            DataInputStream reader = new DataInputStream(new FileInputStream(_tableFile));
            int columnsNumber = _tableInfo.get_columnsInfo().size();
            Boolean fileNotEnded = true;

            while (fileNotEnded) {
                try {

                    Object[] row = new Object[columnsNumber];
                    for (int i = 0; i < columnsNumber; ++i) {
                        ColumnMetaInfo column = _tableInfo.get_columnsInfo().get(i);
                        switch (column.get_columnType().get_type()) {
                        case decimal:
                            row[i] = reader.readDouble();
                            break;
                        case varchar:
                            row[i] = reader.readUTF();
                            break;
                        case integer:
                            row[i] = reader.readInt();
                            break;
                        default:
                            throw new ValidationException("Unsupported");
                        }
                    }
                    objects.add(row);
                } catch (IOException e) {
                    fileNotEnded = false;
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return objects;
    }

}