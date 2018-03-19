package minDb.Json;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import minDb.Core.Exceptions.ValidationException;
import minDb.Core.MetaInfo.DatabaseMetaInfo;
import minDb.MetaInfoRepository.JsonMetaInfoRepository;

/**
 * JsonTests
 */
public class JsonRepoTests {
    @Test
    public void test() throws ValidationException
    {
        DatabaseMetaInfo info = new JsonMetaInfoRepository().getDatabaseMetaInfo("");

        assertNull(info);
    }
    
}