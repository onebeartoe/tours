
package org.onebeartoe.tours.java.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Roberto Marquez
 * 
 *//**
 *
 * @author Roberto Marquez
 * 
 */
public class HtmlToPdf 
{
    public static void main(String [] args) throws FileNotFoundException, DocumentException, IOException
    {
        String k = "<html><body> This is my Project </body></html>";
        File outfile = new File("./test.pdf");
        OutputStream file = new FileOutputStream(outfile);
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, file);
        document.open();
        InputStream is = new ByteArrayInputStream(k.getBytes());
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
        document.close();
        file.close();
    }
}
