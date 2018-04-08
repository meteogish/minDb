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

    public DatabaseMetaInfo getDatabaseMetaInfo(File dbFile) throws ValidationException {
        try {
            if(!dbFile.getName().endsWith(".db"))
            {
                throw new ValidationException("Invalid database info file extension.");
            }

            if (!dbFile.exists()) {
                throw new ValidationException("The database file is not exists by path.");
            }

            Gson gson = new Gson();

            FileReader fileReader = new FileReader(dbFile);
            JsonReader jsonReader = new JsonReader(fileReader);
            DatabaseMetaInfo metaInfo = gson.fromJson(jsonReader, DatabaseMetaInfo.class);
            jsonReader.close();
            fileReader.close();
            return metaInfo;
        } catch (Exception ex) {
            throw new ValidationException("An exception during db info read process. " + ex.getMessage());
        }
    }

    public DatabaseMetaInfo getDatabaseMetaInfo(String path) throws ValidationException {
        File file = new File(path);
        return getDatabaseMetaInfo(file);
    }

    public void saveDatabaseMetaInfo(DatabaseMetaInfo info, String folderPath, String name) throws ValidationException {
        if (info == null) {
            throw new ValidationException("DbInfo object is null for saving");
        }

        if (StringExtenstions.IsNullOrEmpty(folderPath)) {
            throw new ValidationException("FolderPath is null/empty/whitespace");
        }

        if (StringExtenstions.IsNullOrEmpty(name)) {
            throw new ValidationException("DbName is null/empty/whitespace");
        }

        if (!name.endsWith(".db")) {
            name = name + ".db";
        }
        File file = new File(folderPath, name);
        saveDatabaseMetaInfo(info, file);        
    }

    public void saveDatabaseMetaInfo(DatabaseMetaInfo info, File file) throws ValidationException {
        try {
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
            throw new ValidationException("An exception during db info write. " + ex.getMessage());
        }
    }

}