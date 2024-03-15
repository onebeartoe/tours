
package org.onebeartoe.tours.poi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * This class demonstrates text alignment in a Word Document.
 */
public class PoiDocuments
{
    public void writeDocument(OutputStream outStream, XWPFDocument document) 
            throws FileNotFoundException, IOException 
    {
        // save the docs
        
        document.write(outStream);       
        
        outStream.close();
    }
}
