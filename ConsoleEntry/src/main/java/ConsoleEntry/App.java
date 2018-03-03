package ConsoleEntry;

import minDb.SqlQueryParser.*;
import minDb.Core.Components.IQueryParser;

public class App {

    public static void main(String[] args) {
        IQueryParser parser = new SqlQueryParser();
        System.out.println(parser.parse("statement") == null);
    }

}