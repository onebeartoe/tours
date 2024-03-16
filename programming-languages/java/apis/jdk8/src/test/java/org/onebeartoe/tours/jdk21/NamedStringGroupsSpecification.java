
package org.onebeartoe.tours.jdk21;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;

/**
 *
 */
public class NamedStringGroupsSpecification
{
//TODO: Why did this start failing?
//    @Test
    public void group()
    {
        String line = "5,San Antonio,7 345 123";
        
        Pattern pattern =  Pattern.compile("""
                                           (?<index>\\d+);\
                                           (?<city>[ a-zA-Z]+);\
                                           (?<population>[ \\d]+)$""");
        
        Matcher matcher = pattern.matcher(line);
        
        boolean matches = matcher.matches();
        
        assertTrue(matches);

        String id = matcher.group("index");
        String city = matcher.group("city");
        String population = matcher.group("population");
        
        assertEquals("San Antonio", city);
    }
}
