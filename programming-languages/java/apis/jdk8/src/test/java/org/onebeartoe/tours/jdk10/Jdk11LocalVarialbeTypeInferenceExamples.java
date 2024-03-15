
package org.onebeartoe.tours.jdk10;

import org.testng.annotations.Test;

/**
 *
 * @author Roberto Marquez
 */
public class Jdk11LocalVarialbeTypeInferenceExamples
{
    @Test
    public void localVarialbeTypeInference()
    {
        var s = new String("www");
        
        System.out.println(s);
    }    
}
