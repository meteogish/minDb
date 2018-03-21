package minDb.QueryBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnMetaInfo;
import minDb.Core.MetaInfo.ColumnType;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Aggregation;
import minDb.Core.QueryModels.Join;
import minDb.Core.QueryModels.Query;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.Table;
import minDb.Core.QueryModels.ValueCompare;
import minDb.QueryBuilder.CreateTable.CreateQueryFinder;
import minDb.QueryBuilder.Select.FromTableFinder;
import minDb.QueryBuilder.Select.JoinsFinder;
import minDb.QueryBuilder.Select.SelectColumnsFinder;
import minDb.SqlQueryParser.QueryParser;

/**
 * QueryBuilderTests
 */
public class QueryBuilderTest {

    QueryParser parser;

    @Before
    public void ctor()
    {
        parser = new QueryParser(
            new CreateQueryFinder(), 
            new FromTableFinder(),
            new SelectColumnsFinder(),
            new JoinsFinder());
    }

    @Test
    public void SelectColumns_PositiveTest() throws ValidationException {
        String strQuery = "select Id as i, Another, count(ROI) as roi from Customers c";

        List<SelectColumn> expectedSelectColumns = new ArrayList<SelectColumn>(3);
        expectedSelectColumns.add(new SelectColumn("Id", "i", null));
        expectedSelectColumns.add(new SelectColumn("Another", null, null));
        expectedSelectColumns.add(new SelectColumn("ROI", "roi", Aggregation.Count));
        
        Query actualQuery = parser.parse(strQuery);

        assertEquals("Customers", actualQuery.get_fromTable().get_name());
        assertEquals("c", actualQuery.get_fromTable().get_alias());

        List<SelectColumn> actualSelectColumns = actualQuery.get_select();
        assertEquals(3, actualSelectColumns.size());

        for(int i = 0; i < actualSelectColumns.size(); ++i)
        {
            assertEquals(expectedSelectColumns.get(i).get_name(), actualSelectColumns.get(i).get_name());
            assertEquals(expectedSelectColumns.get(i).get_alias(), actualSelectColumns.get(i).get_alias());
            assertEquals(expectedSelectColumns.get(i).get_aggregate(), actualSelectColumns.get(i).get_aggregate());
        }
    }

    @Test
    public void Joins_PositiveTest() throws ValidationException
    {
        String strQuery = "select * from Employees e " + 
                        "join EmployeeTerritories et on et.EmployeeID = e.EmployeeID " + 
                        "join Territories t on t.TerritoryID = e.TerritoryID";
        
        Table e = new Table("Employees", "e");
        Table et = new Table("EmployeeTerritories", "et");
        Table t = new Table("Territories", "t");
        

        List<Join> expectedJoins = new ArrayList<Join>(2);
        expectedJoins.add(Join.table(et).on("EmployeeID", et, ValueCompare.Equals, "EmployeeID", e));
        expectedJoins.add(Join.table(t).on("TerritoryID", t, ValueCompare.Equals, "TerritoryID", et));

        
        Query actualQuery = parser.parse(strQuery);

        assertEquals("Employees", actualQuery.get_fromTable().get_name());
        assertEquals("e", actualQuery.get_fromTable().get_alias());

        List<SelectColumn> actualSelectColumns = actualQuery.get_select();
        assertEquals(0, actualQuery.get_select().size());

        List<Join> actualJoins = actualQuery.get_joins();
        assertEquals(expectedJoins.size(), actualJoins.size());


        for(int i = 0; i < actualJoins.size(); ++i)
        {
            assertEquals(expectedJoins.get(i).get_table().get_name(), actualJoins.get(i).get_table().get_name());
            assertEquals(expectedJoins.get(i).get_table().get_alias(), actualJoins.get(i).get_table().get_alias());


            assertEquals(expectedJoins.get(i).get_conditions().get(0).get_leftColumn().get_name(), expectedJoins.get(i).get_conditions().get(0).get_leftColumn().get_name());
            assertEquals(expectedJoins.get(i).get_conditions().get(0).get_rightColumn().get_name(), expectedJoins.get(i).get_conditions().get(0).get_rightColumn().get_name());

            assertEquals(expectedJoins.get(i).get_conditions().get(0).get_leftColumn().get_table().get_name(), expectedJoins.get(i).get_conditions().get(0).get_leftColumn().get_table().get_name());
            assertEquals(expectedJoins.get(i).get_conditions().get(0).get_rightColumn().get_table().get_name(), expectedJoins.get(i).get_conditions().get(0).get_rightColumn().get_table().get_name());
        
        }        
    }

    @Test
    public void CreateTable_Test() throws ValidationException
    {
        String createQuery = "create table Customers(Id integer, Name varchar(10))";
        // String createQuery = "create database Test";

        Table expectedTable = new Table("Customers");

        List<ColumnMetaInfo> expectedColumns = new ArrayList<ColumnMetaInfo>(2);
        expectedColumns.add(new ColumnMetaInfo(new ColumnType(ColumnType.Type.integer, -1), "Id"));
        expectedColumns.add(new ColumnMetaInfo(new ColumnType(ColumnType.Type.varchar, 10),"Name"));       

        Query actualQuery =  parser.parse(createQuery);
        TableMetaInfo info = actualQuery.get_createTableInfo();

        assertNotNull(info);

        List<ColumnMetaInfo> actualColumns = info.get_columnsInfo();
        assertNotNull(actualColumns);
        assertEquals(expectedColumns.size(), actualColumns.size());

        for(int i = 0; i < expectedColumns.size(); ++i)
        {
            assertEquals(expectedColumns.get(i).get_name(), actualColumns.get(i).get_name());
            assertEquals(expectedColumns.get(i).get_columnType().get_type(), actualColumns.get(i).get_columnType().get_type());            
            assertEquals(expectedColumns.get(i).get_columnType().get_length(), actualColumns.get(i).get_columnType().get_length());            
        }
    }
}