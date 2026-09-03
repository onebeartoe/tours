
package org.onebeartoe.tours.initialization;

import static org.junit.Assert.assertEquals;
import org.onebeartoe.tours.initialization.FluentInitialization.FluentStackPane;
import org.testng.annotations.Test;

/**
 * This test show cases a basic fluent builder for an object.
 */
public class FluentInitializationTest 
{
    @Test
    public void fluentBuilder()
    {
        var stackPane = new FluentStackPane.Builder()
                                .style("colorful")
                                .width(10)
                                .height(15)
                                .build();
                                   
        assertEquals("colorful", stackPane.style);
        assertEquals(10, stackPane.width);
        assertEquals(15, stackPane.height);
    }    
}
