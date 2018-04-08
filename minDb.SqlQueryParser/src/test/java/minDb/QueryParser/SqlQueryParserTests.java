package minDb.QueryParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnMetaInfo;
import minDb.Core.MetaInfo.ColumnType;
import minDb.Core.MetaInfo.TableMetaInfo;
import minDb.Core.QueryModels.Join;
import minDb.Core.QueryModels.SelectColumn;
import minDb.Core.QueryModels.Table;
import minDb.Core.QueryModels.Conditions.ColumnCondition.Compare;
import minDb.Core.QueryModels.Conditions.LogicalCondition;
import minDb.Core.QueryModels.Conditions.ValueColumnCondition;
import minDb.Core.QueryModels.Queries.InsertQuery;
import minDb.Core.QueryModels.Queries.Query;
import minDb.Core.QueryModels.Queries.SelectQuery;
import minDb.SqlQueryParser.QueryParser;
import minDb.SqlQueryParser.Adapter.Create.CreateQueryFinder;
import minDb.SqlQueryParser.Adapter.Insert.InsertQueryFinder;
import minDb.SqlQueryParser.Adapter.Primitives.IPrimitivesAdapter;
import minDb.SqlQueryParser.Adapter.Primitives.SqlPrimitivesAdapter;
import minDb.SqlQueryParser.Adapter.Select.JoinsFinder;
import minDb.SqlQueryParser.Adapter.Select.SelectColumnsFinder;
import minDb.SqlQueryParser.Adapter.Select.WhereFinder;

/**
 * QueryBuilderTests
 */
public class SqlQueryParserTests {

    QueryParser parser;

    @Before
    public void ctor() {
        IPrimitivesAdapter primitivesAdapter = new SqlPrimitivesAdapter();
        parser = new QueryParser(
            new CreateQueryFinder(),
            primitivesAdapter,
            new InsertQueryFinder(primitivesAdapter),
            new SelectColumnsFinder(primitivesAdapter),
            new JoinsFinder(primitivesAdapter),
            new WhereFinder(primitivesAdapter));
    }

    @Test
    public void SelectColumnsTest() throws ValidationException {
        String strQuery = "select Id as i, c.Another, t.TerLoc as Location from Customers c join Territories t";

        Table customersTable = new Table("Customers", "c");
        Table territoriesTable = new Table("Territories", "t");


        List<SelectColumn> expectedSelectColumns = new ArrayList<SelectColumn>(3);
        expectedSelectColumns.add(new SelectColumn("Id", customersTable, "i"));
        expectedSelectColumns.add(new SelectColumn("Another", customersTable, null));
        expectedSelectColumns.add(new SelectColumn("TerLoc", territoriesTable, "Location"));

        SelectQuery actualQuery = parser.parse(strQuery).get_select();

        assertEquals(customersTable.get_name(), actualQuery.get_table().get_name());
        assertEquals(customersTable.get_alias(), actualQuery.get_table().get_alias());

        List<SelectColumn> actualSelectColumns = actualQuery.get_select();
        assertEquals(3, actualSelectColumns.size());

        for (int i = 0; i < actualSelectColumns.size(); ++i) {
            assertEquals(expectedSelectColumns.get(i).get_name(), actualSelectColumns.get(i).get_name());
            assertEquals(expectedSelectColumns.get(i).get_alias(), actualSelectColumns.get(i).get_alias());
            assertEquals(expectedSelectColumns.get(i).get_table().get_name(), actualSelectColumns.get(i).get_table().get_name());
            assertEquals(expectedSelectColumns.get(i).get_table().get_alias(), actualSelectColumns.get(i).get_table().get_alias());            
        }
    }

    @Test
    public void JoinsTest() throws ValidationException {
        String strQuery = "select * from Employees e " 
                + "join EmployeeTerritories et on et.EmployeeID = e.EmployeeID "
                + "join Territories t on e.TerritoryID = t.TerritoryID";

        Table e = new Table("Employees", "e");
        Table et = new Table("EmployeeTerritories", "et");
        Table t = new Table("Territories", "t");

        List<Join> expectedJoins = new ArrayList<Join>(2);
        expectedJoins.add(Join.table(et).on("EmployeeID", et, Compare.EQUALS, "EmployeeID", e));
        expectedJoins.add(Join.table(t).on("TerritoryID", t, Compare.EQUALS, "TerritoryID", et));

        SelectQuery actualQuery = parser.parse(strQuery).get_select();

        assertEquals("Employees", actualQuery.get_table().get_name());
        assertEquals("e", actualQuery.get_table().get_alias());

        List<SelectColumn> actualSelectColumns = actualQuery.get_select();
        assertNotNull(actualSelectColumns);
        assertEquals(0, actualSelectColumns.size());

        List<Join> actualJoins = actualQuery.get_joins();
        assertEquals(expectedJoins.size(), actualJoins.size());

        for (int i = 0; i < actualJoins.size(); ++i) {
            assertEquals(expectedJoins.get(i).get_table().get_name(), actualJoins.get(i).get_table().get_name());
            assertEquals(expectedJoins.get(i).get_table().get_alias(), actualJoins.get(i).get_table().get_alias());

            assertEquals(expectedJoins.get(i).get_conditions().get(0).get_leftColumn().get_name(),
                    expectedJoins.get(i).get_conditions().get(0).get_leftColumn().get_name());
            assertEquals(expectedJoins.get(i).get_conditions().get(0).get_rightColumn().get_name(),
                    expectedJoins.get(i).get_conditions().get(0).get_rightColumn().get_name());

            assertEquals(expectedJoins.get(i).get_conditions().get(0).get_leftColumn().get_table().get_name(),
                    expectedJoins.get(i).get_conditions().get(0).get_leftColumn().get_table().get_name());
            assertEquals(expectedJoins.get(i).get_conditions().get(0).get_rightColumn().get_table().get_name(),
                    expectedJoins.get(i).get_conditions().get(0).get_rightColumn().get_table().get_name());

        }
    }

    @Test
    public void CreateTableTest() throws ValidationException {
        String createQuery = "create table Customers(Id INT, Name varchar(10))";
        // String createQuery = "create database Test";

        String expectedTableName = "Customers";

        List<ColumnMetaInfo> expectedColumns = new ArrayList<ColumnMetaInfo>(2);
        expectedColumns.add(new ColumnMetaInfo(new ColumnType(ColumnType.Type.INT, null), "Id"));
        expectedColumns.add(new ColumnMetaInfo(new ColumnType(ColumnType.Type.VARCHAR, 10), "Name"));

        Query actualQuery = parser.parse(createQuery);
        TableMetaInfo info = actualQuery.get_createTableInfo();

        assertNotNull(info);
        assertEquals(expectedTableName, info.get_tableName());

        List<ColumnMetaInfo> actualColumns = info.get_columnsInfo();
        assertNotNull(actualColumns);
        assertEquals(expectedColumns.size(), actualColumns.size());

        for (int i = 0; i < expectedColumns.size(); ++i) {
            assertEquals(expectedColumns.get(i).get_name(), actualColumns.get(i).get_name());
            assertEquals(expectedColumns.get(i).get_columnType().get_type(),
                    actualColumns.get(i).get_columnType().get_type());
            assertEquals(expectedColumns.get(i).get_columnType().get_length(),
                    actualColumns.get(i).get_columnType().get_length());
        }
    }

    @Test
    public void InsertQuery_Test() throws ValidationException {
        String insertQuery = "insert into Accounts(Id, Salary, Name, Surname) values (23, 23.045, 'TestName', 'TestSurname')";

        List<String> expectedColumns = Arrays.asList("Id", "Salary", "Name", "Surname");
        List<Object> expectedValues = Arrays.asList((long)23, 23.045, "TestName", "TestSurname");

        InsertQuery q = parser.parse(insertQuery).get_insert();

        List<Object> actualValues = q.get_insertValues();
        List<String> actualColumns = q.get_insertColumns();

		assertEquals(expectedValues.size(), actualValues.size());
		assertEquals(expectedColumns.size(), actualColumns.size());        
        
        for (int i = 0; i < expectedValues.size(); ++i) {
            assertEquals(expectedValues.get(i), actualValues.get(i));
            assertEquals(expectedColumns.get(i), actualColumns.get(i));
        }
    }

    @Test
    public void InsertAll_Query_Test() throws ValidationException {
        String insertQuery = "insert into Accounts values (23, 23.045, 'TestName', 'TestSurname')";

        List<Object> expectedValues = Arrays.asList((long)23, 23.045, "TestName", "TestSurname");
        
        InsertQuery q = parser.parse(insertQuery).get_insert();

        List<Object> actualValues = q.get_insertValues();
        List<String> actualColumns = q.get_insertColumns();

		assertEquals(expectedValues.size(), actualValues.size());
		assertEquals(0, actualColumns.size());        
        
        for (int i = 0; i < expectedValues.size(); ++i) {
            assertEquals(expectedValues.get(i), actualValues.get(i));
        }
    }

    @Test
    public void WhereAndTest_WithJoin()
    {
        String insertQuery = "select * from Account r join Info i on i.Id = r.Df where i.Id <> 23 and r.Surname > 'HAHA'";

        SelectQuery q = null;
		try {
			q = parser.parse(insertQuery).get_select();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        LogicalCondition and = (LogicalCondition) q.get_where();
        ValueColumnCondition left = (ValueColumnCondition) and.get_leftCondition();
        ValueColumnCondition right = (ValueColumnCondition) and.get_righCondition();

        assertEquals("Id", left.get_leftColumn().get_name());
        assertEquals("Info", left.get_leftColumn().get_table().get_name());
        assertEquals(23, ((Long)left.get_value()).intValue());
        assertEquals(Compare.NOT_EQUALS, left.get_compare());

        assertEquals("Surname", right.get_leftColumn().get_name());
        assertEquals("Account", right.get_leftColumn().get_table().get_name());
        assertEquals("HAHA", right.get_value());
        assertEquals(Compare.GREATER, right.get_compare());
        
    }

    @Test
    public void Where_ReverseCondition_AndIsNullTest()
    {
        String insertQuery = "select * from Account r where 23 < Id and r.Surname is null";

        SelectQuery q = null;
		try {
			q = parser.parse(insertQuery).get_select();
		} catch (ValidationException e) {
			e.printStackTrace();
		}

        LogicalCondition and = (LogicalCondition) q.get_where();
        ValueColumnCondition left = (ValueColumnCondition) and.get_leftCondition();
        ValueColumnCondition right = (ValueColumnCondition) and.get_righCondition();

        assertEquals("Id", left.get_leftColumn().get_name());
        assertEquals("Account", left.get_leftColumn().get_table().get_name());
        assertEquals(23, ((Long)left.get_value()).intValue());
        assertEquals(Compare.LESS, left.get_compare());

        assertEquals("Surname", right.get_leftColumn().get_name());
        assertEquals("Account", right.get_leftColumn().get_table().get_name());
        assertNull(right.get_value());
    }
}