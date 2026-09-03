
package org.onebeartoe.tours.initialization;

import static org.junit.Assert.*;
import org.testng.annotations.Test;

/**
 *
 */
public class BlockScopeInitializationTest 
{    
    public BlockScopeInitializationTest() 
    {
        
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testNewPane() 
    {
        final String style = "plain";
        int min = 3;
        int max = 9;
        
        BlockScopeInitialization blockScope = new BlockScopeInitialization();
      

//        BlockScopeInitialization.StackPane pane = BlockScopeInitialization.newPane(style, min, max);
        BlockScopeInitialization.StackPane pane = blockScope.newPane(style, min, max);
        
        // expect the fields to be set but are null
        assertEquals(style, pane.style); // throws a NPE
        assertEquals(min, pane.min);
        assertEquals(max, pane.max);
    }

    @Test
    public void testNewContructorPane() 
    {
        final String style = "plain";
        int min = 3;
        int max = 9;
        
        BlockScopeInitialization blockScope = new BlockScopeInitialization();
      

        BlockScopeInitialization.StackPane pane = new BlockScopeInitialization.StackPane();
        pane. setStyle(style);
            pane.setMaxHeight(min);
            pane.setMaxWidth(max);
//        BlockScopeInitialization.StackPane pane = blockScope.newPane(style, min, max);
        
        assertEquals(style, pane.style);
        assertEquals(min, pane.min);
        assertEquals(max, pane.max);
    }
}
