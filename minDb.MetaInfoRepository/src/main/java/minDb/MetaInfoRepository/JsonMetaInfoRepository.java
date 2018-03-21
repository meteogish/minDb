package minDb.MetaInfoRepository;

import java.io.File;
import java.io.FileReader;

import com.alibaba.fastjson.util.IOUtils;
import com.google.gson.Gson;

import minDb.Core.Components.IMetaInfoRepository;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;

/**
 * JsonMetaInfoRepository
 */
public class JsonMetaInfoRepository implements IMetaInfoRepository {

    public DatabaseMetaInfo getDatabaseMetaInfo(String path) throws ValidationException {
        // String input = "{\"userId\" : \"LDURAJ\", \"token\" : \"TOKEN\", \"language\": \"eng\", \"client\" : \"100\" }"; 
        // JsonObject object = Json.parse(input).asObject();
        // String userId = object.get("userId").asString();
        // int clienId = object.get("client").asInt();
        // List<TableMetaInfo> tables = new ArrayList<TableMetaInfo>();
        // List<ColumnMetaInfo> columnsInfo = new ArrayList<ColumnMetaInfo>();
        // columnsInfo.add(new ColumnMetaInfo(new ColumnType(ColumnType.Type.integer), "id"));
        // columnsInfo.add(new ColumnMetaInfo(new ColumnType(ColumnType.Type.varchar, 20), "name"));

        // tables.add(new TableMetaInfo(columnsInfo, "Customer"));

        // DatabaseMetaInfo db = new DatabaseMetaInfo(tables);

        //String str = "";
        // try {

        //     JSON.parseObject(is, type, features)
        //      str = JSON.toJSONString(db);
        //      File file = new File("c:/Users/yevhenii.kyshko/out.json");
        //      if(file.createNewFile())
        //      {
        //         FileWriter fileWriter = new FileWriter(file);
        //         fileWriter.write(str);                
        //         fileWriter.flush();
        //         fileWriter.close();
        //      }
        // } catch (Exception ex) {
        //     System.out.println(ex.getStackTrace());
        // }
        try{
            File file = new File(path);
            if(!file.exists())
            {
                throw new ValidationException("The database file is not exists by path.");
            }

            Gson gson = new Gson();

            
            FileReader reader = new FileReader(file);
            String jsonStr = IOUtils.readAll(reader);
            reader.close();
            DatabaseMetaInfo metaInfo = gson.fromJson(jsonStr, DatabaseMetaInfo.class);
            // SerializeConfig.globalInstance.configEnumAsJavaBean(ColumnType.Type.class);
            // DatabaseMetaInfo metaInfo = JSON.parseObject(jsonStr, DatabaseMetaInfo.class);
            return metaInfo;
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}