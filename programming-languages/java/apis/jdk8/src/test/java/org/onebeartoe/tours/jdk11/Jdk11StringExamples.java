
package org.onebeartoe.tours.jdk11;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;

/**
 *
 * @author Roberto Marquez
 */
public class Jdk11StringExamples
{    
    @Test
    public void isBlank()
    {
        // yay, finaly a built-in isBlank()
        
        String blank = "  \t  \n   ";
        
        assertTrue( blank.isBlank() );
    }
    
    @Test
    public void isNotBlank()
    {
        // yay, finaly a built-in isBlank()
        
        String blank = "w  \t  \n   ";
        
        assertFalse( blank.isBlank() );
    }
}
