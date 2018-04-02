package minDb.DataProvider;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import minDb.Core.Components.Data.IRawTableReader;
import minDb.Core.Components.Data.IRawTableWriter;
import minDb.Core.Components.Data.ITableFileProvider;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnMetaInfo;
import minDb.Core.MetaInfo.ColumnType;
import minDb.Core.MetaInfo.ColumnType.Type;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.DataProvider.Data.IO.TableFileProvider;
import minDb.DataProvider.Data.IO.TableReader;
import minDb.DataProvider.Data.IO.TableWriter;
import minDb.DataProvider.Data.TypeSizeProvider;
/**
 * TableIOTests
 */
public class DataProviderTests {

    private IRawTableReader _reader;
    private IRawTableWriter _writer;
    private TableMetaInfo _tableInfo;
    private ITableFileProvider _tableFileProvider;

    File _tableFile;

    public DataProviderTests() throws ValidationException {
        TypeSizeProvider typeSizeProvider = new TypeSizeProvider();
        _reader = new TableReader(typeSizeProvider);
        _writer = new TableWriter(typeSizeProvider);
        
        List<ColumnMetaInfo> columnsInfo = new ArrayList<ColumnMetaInfo>(4);
        columnsInfo.add(new ColumnMetaInfo(new ColumnType(Type.VARCHAR, 15), "TestString1"));
        columnsInfo.add(new ColumnMetaInfo(new ColumnType(Type.INT, null), "TestInt")); 
        columnsInfo.add(new ColumnMetaInfo(new ColumnType(Type.DOUBLE, null), "TestDouble"));
        columnsInfo.add(new ColumnMetaInfo(new ColumnType(Type.VARCHAR, 7), "TestString2"));
        
        _tableInfo = new TableMetaInfo(columnsInfo, "TestTable");

        _tableFileProvider = new TableFileProvider();
    }

    @Before
    public void createFile() throws ValidationException
    {
        _tableFile = _tableFileProvider.getTableFile("Test", ".", true);
    }

    @After
    public void deleteFile()
    {
        if(_tableFile != null && _tableFile.exists())
        {
            _tableFile.delete();
        }
    }

    @Test
    public void FullIOTest() throws ValidationException
    {
        List<Object> expectedValues = new ArrayList<Object>();
        expectedValues.add("FULLL_123STIRNG");
        expectedValues.add(23);
        expectedValues.add(23.082001);
        expectedValues.add("STRING");

        _writer.writeTo(_tableInfo, _tableFile, expectedValues);

        List<List<Object>> actualValues = _reader.readFrom(_tableInfo, _tableFile);

        for (int i = 0; i < expectedValues.size(); i++) {
            assertEquals(expectedValues.get(i), actualValues.get(0).get(i));
        }
    }
    
}