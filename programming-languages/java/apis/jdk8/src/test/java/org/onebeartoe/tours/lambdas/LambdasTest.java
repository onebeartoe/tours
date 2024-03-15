
package org.onebeartoe.tours.lambdas;

import static org.testng.Assert.assertNotEquals;
import org.testng.annotations.Test;

public class LambdasTest 
{
    @Test
    public void objectStateMutaionOccurs()
    {
        var originalValue = 33;
        
        int[] total = new int[1];

        total[0] = originalValue;

        System.out.println("total[0] =  " + total[0]);

        Runnable r = () -> total[0]++;

        r.run();

        System.out.println("after total[0] =  " + total[0]);

        assertNotEquals(total[0], originalValue);
    }
    
}
