
package org.onebeartoe.tours.initialization;

/**
 *
 * I may have seen this block scope initialization early in Java,
 * but I did not quite understand it.
 */
public class BlockScopeInitialization 
{
    public static void main(String[] args) 
    {
        var app = new BlockScopeInitialization();
        
        app.demo();
    }

    private void demo() 
    {
        StackPane pane = new StackPane()
        {{
            // Note that 'pane.' is not needed in the next few setter calls
            setStyle("-fx-padding: 8px 5px 0px 5px;-fx-background-color: black;-fx-shape: \"M0 0 L1 0 L.5 1 Z\";");
            setMaxHeight(10);
            setMaxWidth(15);
        }};        
    }
    
    class StackPane
    {

        public void setStyle(String fxpadding_8px_5px_0px_5pxfxbackgroundcolo) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public void setMaxHeight(int i) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public void setMaxWidth(int i) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }	

//	private StackPane downArrow = StackPaneBuilder.create().style("-fx-padding: 8px 5px 0px 5px;-fx-background-color: black;-fx-shape: \"M0 0 L1 0 L.5 1 Z\";").maxHeight(10).maxWidth(15).build();
        
        
    }
}
