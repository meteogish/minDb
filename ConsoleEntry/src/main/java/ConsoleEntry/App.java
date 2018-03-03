package ConsoleEntry;

import minDb.Core.IQueryStatement;
import minDb.SqlQuery.QueryStatement;

public class App {

    public static void main(String[] args) {
        IQueryStatement st = new QueryStatement();
        st.Build();
    }

}