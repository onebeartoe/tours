
package org.onebeartoe.tours.regular.expressions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;

/**
 * The Java regular expressions for filenames are presented here.
 */
public class FileNameRegularExpressions
{    
    @Test
    public void starDotExtensionWithPattern()
    {        
        String regularExpression = "\\\\*.text";
        
        Pattern pattern = Pattern.compile(regularExpression);
             
        String input = "some.text";
        Matcher foundMatcher = pattern.matcher(input);                        
        assertTrue(foundMatcher.find() );
        
        String input2 = "not.even";
        Matcher notFoundMatcher = pattern.matcher(input2);
        assertFalse( notFoundMatcher.find() );
    }
}
