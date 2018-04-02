package minDb.Core.Components.Data;

import java.io.File;

import minDb.Core.Exceptions.ValidationException;

/**
 * ITableFileProvider
 */
public interface ITableFileProvider {
    File getTableFile(String name, String dbFolder, Boolean createIfNotExists) throws ValidationException;    
}