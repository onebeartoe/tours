
package org.onebeartoe.tours.java.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

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
        HtmlToPdf converter = new HtmlToPdf();
        
        String rawHtml = "<html><body> This is my <br/> Project </body></html>";
        String outfile = "./test.pdf";
        converter.htmlToPdf(rawHtml, outfile);

        String url = "http://electronics.onebeartoe.org/3d-realization/printers/printrbot-simple-metal/upgrades/lcd-with-rotary-button/assembly";
               url = "http://localhost:8080/electronics/3d-printing/printers/printrbot-simple-metal/upgrades/lcd-with-rotary-button/assembly/";
                
        URL inputUrl = new URL(url);        
        String urlOutfile = "./url-test.pdf";
        converter.htmlToPdf(inputUrl, urlOutfile);
    }
    
    public void htmlToPdf(String html, String outfilePath) throws FileNotFoundException, DocumentException, IOException
    {
        File outfile = new File(outfilePath);
        OutputStream file = new FileOutputStream(outfile);
        
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, file);
        document.open();
        
        InputStream is = new ByteArrayInputStream(html.getBytes());
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
        document.close();
        file.close();        
    }
    
    public void htmlToPdf(URL inputUrl, String outfilePath) throws FileNotFoundException, DocumentException, IOException
    {
        String html = readUrlContents(inputUrl);
        
        htmlToPdf(html, outfilePath);
    }
    
    private String readUrlContents(URL inputUrl) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        
        InputStream is = inputUrl.openStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        br.lines().forEach(line ->
        {
            sb.append(line);
            sb.append(System.lineSeparator());
        });
        
        return sb.toString();
    }
}
