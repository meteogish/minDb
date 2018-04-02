package minDb.DataProvider.Data.IO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import minDb.Core.Components.Data.ITableFileProvider;
import minDb.Core.Exceptions.ValidationException;

/**
 * TableFileProvider
 */
public class TableFileProvider implements ITableFileProvider {

    @Override
    public File getTableFile(String name, String dbFolder, Boolean createIfNotExists) throws ValidationException {
        Path fullFilePath = Paths.get(dbFolder, name + ".tb");
        File tableFile = new File(fullFilePath.toUri());

        if (!tableFile.exists()) {
            if (createIfNotExists) {
                try {
                    tableFile.createNewFile();
                } catch (IOException e) {
                    throw new ValidationException("Table file not exists.");
                }
            } else {
                throw new ValidationException("Table file not exists.");
            }
        }
        return tableFile;
    }
}