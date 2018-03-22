package minDb.MetaInfoRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import minDb.Core.Components.IMetaInfoRepository;
import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.Extensions.StringExtenstions;

/**
 * JsonMetaInfoRepository
 */
public class JsonMetaInfoRepository implements IMetaInfoRepository {

    public DatabaseMetaInfo getDatabaseMetaInfo(String path) throws ValidationException {
        try {
            File file = new File(path);
            if (!file.exists()) {
                throw new ValidationException("The database file is not exists by path.");
            }

            Gson gson = new Gson();

            FileReader fileReader = new FileReader(file);
            JsonReader jsonReader = new JsonReader(fileReader);
            DatabaseMetaInfo metaInfo = gson.fromJson(jsonReader, DatabaseMetaInfo.class);
            jsonReader.close();
            fileReader.close();
            return metaInfo;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void saveDatabaseMetaInfo(DatabaseMetaInfo info, String folderPath, String name) throws ValidationException {
        if(info == null)
        {
            throw new ValidationException("DbInfo object is null for saving");
        }

        if(StringExtenstions.IsNullOrEmpty(folderPath))
        {
            throw new ValidationException("FolderPath is null/empty/whitespace");
        }

        if(StringExtenstions.IsNullOrEmpty(name))
        {
            throw new ValidationException("DbName is null/empty/whitespace");
        }
        
        if (!name.endsWith(".db")) {
            name = name + ".db";
        }

        Path folder = Paths.get(folderPath, name);

        System.out.println(folder.toUri());

        try {
            File file = new File(folder.toUri());

            if (!file.exists()) {

                file.createNewFile();
            }

            Gson gson = new Gson();
            String infoJson = gson.toJson(info);

            FileWriter fileWriter = new FileWriter(file);
            JsonWriter jsonWriter = new JsonWriter(fileWriter);
            jsonWriter.jsonValue(infoJson);
            jsonWriter.close();
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}