
package org.onebeartoe.tours.poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabs;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;

/**
 *
 */
public class PoiDocumentsExamples
{
    PoiDocuments implementation = new PoiDocuments();
    
    @Test
    public void leftAndRightAlignedTextOnTheSameLine() throws FileNotFoundException, IOException
    {
        File outFile = new File("target/aligned.docx");
        
        OutputStream outStream = new FileOutputStream(outFile);
        
        XWPFDocument document = buildDeftAndRightAlignedTextOnTheSameLine();
        
        implementation.writeDocument(outStream, document);
        
        assertTrue( outFile.exists() );
        
        assertTrue( outFile.length() > 0);
        
        System.out.println("document path = " + outFile.getAbsolutePath() );
        
//TODO: assert the file's content-type is Docx        
    }
    
    /**
     * This TabStop allows for two text items on the same line. One is left aligned
     * and the other is right aligned, on the same line.
     * 
     * Code example is from this StackOverflow answer:
     * 
     *      https://stackoverflow.com/a/33942296/803890
     * 
     * @param oParagraph
     * @param oSTTabJc
     * @param oPos 
     */
    private void setTabStop(XWPFParagraph oParagraph, STTabJc.Enum oSTTabJc, BigInteger oPos) {
        CTP oCTP = oParagraph.getCTP();
        CTPPr oPPr = oCTP.getPPr();
        if (oPPr == null) {
            oPPr = oCTP.addNewPPr();
        }

        CTTabs oTabs = oPPr.getTabs();
        if (oTabs == null) {
            oTabs = oPPr.addNewTabs();
        }

        CTTabStop oTabStop = oTabs.addNewTab();
        oTabStop.setVal(oSTTabJc);
        oTabStop.setPos(oPos);
    }
    
    private XWPFDocument buildDeftAndRightAlignedTextOnTheSameLine()
    {
        XWPFDocument doc = new XWPFDocument();
        
        // This version uses TabStops to have two text items on the same line, but
        // with separate left and right alignment.
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun tmpRun = paragraph.createRun();
        tmpRun.setText("JAN1");
        tmpRun.addTab();
// uncomment for 3 columns            
//            tmpRun.setText("JAN2");
//            tmpRun.addTab();
        tmpRun.setText("JAN3");
// uncomment for 3 columns            
//            BigInteger pos1 = BigInteger.valueOf(4500);
//            setTabStop(paragraph, STTabJc.Enum.forString("center"), pos1);
        BigInteger pos2 = BigInteger.valueOf(9000);
        setTabStop(paragraph, STTabJc.Enum.forString("right"), pos2);

        
        // This version uses a table to make it appear that two text items are 
        // on the same line with separate left and right alignment.  
        // But really the the text items are in separate table cells.
        XWPFTable table = doc.createTable();

        //Creating first Row
        XWPFTableRow row1 = table.getRow(0);
        
        table.setLeftBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "");
        table.setRightBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "");
        table.setBottomBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "");
        table.setTopBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "");
        
        row1.getCell(0).setText("Left TExt");
        
        XWPFDocument tableDoc = new XWPFDocument();
        XWPFParagraph rightText = tableDoc.createParagraph();
        rightText.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun rightTextRun = rightText.createRun();
        rightTextRun.setText("Right Text");
        
        row1.addNewTableCell().setParagraph(rightText);
        
        // this did not align 
        XWPFParagraph p0 = doc.createParagraph();                        
        XWPFRun run0_1 = p0.createRun();
        run0_1.setText("Left Text");
        run0_1.addTab();
        run0_1.addTab();
        run0_1.addTab();
        
        p0.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun run0_2 = p0.createRun();
        run0_2.setText("Right Text - ");

        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER);

        // Set Text to Bold and font size to 22 for first paragraph
        XWPFRun r1 = p1.createRun();
        r1.setBold(true);
        r1.setItalic(true);
        r1.setFontSize(22);
        r1.setText("The first paragraph is bold, italic, Courier and capitalized");
        r1.setFontFamily("Courier");

        XWPFParagraph p2 = doc.createParagraph();
        //Set color for second paragraph
        XWPFRun r2 = p2.createRun();
        r2.setText("The second paragraph is red in color and is embossed");
        r2.setColor("ff0000");
        r2.setEmbossed(true);

        XWPFParagraph p3 = doc.createParagraph();
        //Set strike for third paragraph and capitalization
        XWPFRun r3 = p3.createRun();
        r3.setStrikeThrough(true);
        r3.setCapitalized(true);
        r3.setText("The third paragraph is strike through and is capitalized");

        XWPFParagraph p4 = doc.createParagraph();
        p4.setWordWrapped(true);
        p4.setPageBreak(true);  // new page break
        p4.setIndentationFirstLine(600);

        XWPFRun r4 = p4.createRun();
        r4.setFontSize(40);
        r4.setItalic(true);
        //r4.setTextPosition(100);
        r4.setText("Line 1");
        r4.addBreak();
        r4.setText("Line 2");
        r4.addBreak();
        r4.setText("Line 3");

        return doc;
    }
}
