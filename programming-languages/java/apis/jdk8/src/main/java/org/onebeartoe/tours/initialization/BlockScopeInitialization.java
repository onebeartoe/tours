
package org.onebeartoe.tours.initialization;

/**
 *
 */
public class BlockScopeInitialization 
{
    public static void main(String[] args) 
    {
        var app = new BlockScopeInitialization();
        
        app.demo();
    }
    
    public static StackPane newPane(String style, int min, int max)
    {
        var pane = new StackPane()
        {{
            // Note that 'pane.' is not needed in the next few setter calls
            setStyle(style);
            setMaxHeight(max);
            setMaxWidth(min);
        }};  
        
        return pane;
    }

    private void demo() 
    {
        /*
        * I may have seen this block scope initialization early in Java,
        * but I did not quite understand it.
        *
        * You can call setters right after the contructor without referenceing the 
        * variable.  It is not recomended to use Double Brace Initialization though.
        */
        StackPane pane = new StackPane()
        {{
            // Note that 'pane.' is not needed in the next few setter calls
            setStyle("-fx-padding: 8px 5px 0px 5px;-fx-background-color: black;-fx-shape: \"M0 0 L1 0 L.5 1 Z\";");
            setMaxHeight(10);
            setMaxWidth(15);
        }};        
        
        System.out.println("pane = " + pane.style + " - " + pane.min + " - " + pane.max );
        
        doSomething(pane);
    }

    private void doSomething(StackPane pane) 
    {
        
        System.out.println("somethign pane = " + pane.style + " - " + pane.min + " - " + pane.max );
    }
    
    public static class StackPane
    {
        String style;
        int max;
        int min;
        
        public void setStyle(String fxpadding_8px_5px_0px_5pxfxbackgroundcolo) {
            style = fxpadding_8px_5px_0px_5pxfxbackgroundcolo;
        }

        public void setMaxHeight(int i) {
            min = i;
        }

        public void setMaxWidth(int i) {
            max = i;
        }	

//	private StackPane downArrow = StackPaneBuilder.create().style("-fx-padding: 8px 5px 0px 5px;-fx-background-color: black;-fx-shape: \"M0 0 L1 0 L.5 1 Z\";").maxHeight(10).maxWidth(15).build();
        
        
    }
}
