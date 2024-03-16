
package org.onebeartoe.tours.lambdas.streams.cryptoquote;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;

/**
 *
 */
public class CryptoquoteSpecification
{
    private final Cryptoquote implementation;
    
    public CryptoquoteSpecification() throws URISyntaxException, IOException
    {
        implementation = new Cryptoquote();
    }
    
//    @Test
    /**
     * This test verifies the decode() method specification, given two encrypted 
     * words that have the same starting 3 letters.
     */
    public void decode()
    {
        var str1 = "cucm";
        
        var str2 = "cucb";
        
        List<String> list = implementation.decode(str1, str2);
        
        assertTrue( list.contains("goat"));
        
        assertTrue( list.contains("goal"));
    }
    
    @Test
    /**
     * This test verifies the allWords() method returns the correct count 
     * of known words in the dictionary.
     */
    public void allWords_count()
    {
        List<String> words = implementation.allWords();
        
        var actual = words.size();
        
        var expected = 466_550;
        
        assertEquals(expected, actual);
    }
}
