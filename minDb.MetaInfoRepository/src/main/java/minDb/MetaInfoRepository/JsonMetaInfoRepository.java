package minDb.MetaInfoRepository;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSON;
import minDb.Core.Components.IMetaInfoRepository;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.ColumnMetaInfo;
import minDb.Core.MetaInfo.ColumnType;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Core.MetaInfo.TableMetaInfo;

/**
 * JsonMetaInfoRepository
 */
public class JsonMetaInfoRepository implements IMetaInfoRepository {

    public DatabaseMetaInfo getDatabaseMetaInfo(String path) throws ValidationException {
        // String input = "{\"userId\" : \"LDURAJ\", \"token\" : \"TOKEN\", \"language\": \"eng\", \"client\" : \"100\" }"; 
        // JsonObject object = Json.parse(input).asObject();
        // String userId = object.get("userId").asString();
        // int clienId = object.get("client").asInt();
        List<TableMetaInfo> tables = new ArrayList<TableMetaInfo>();
        List<ColumnMetaInfo> columnsInfo = new ArrayList<ColumnMetaInfo>();
        columnsInfo.add(new ColumnMetaInfo(new ColumnType(ColumnType.Type.integer), "id"));
        columnsInfo.add(new ColumnMetaInfo(new ColumnType(ColumnType.Type.varchar, 20), "name"));

        tables.add(new TableMetaInfo(columnsInfo, "Customer"));

        DatabaseMetaInfo db = new DatabaseMetaInfo(tables);

        String str = "";
        try {
             str = JSON.toJSONString(db);
             File file = new File("c:/Users/yevhenii.kyshko/out.json");
             if(file.createNewFile())
             {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(str);                
                fileWriter.flush();
                fileWriter.close();
             }
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
        }
        System.err.println(str);
        System.out.println(str);

        return null;
    }
}