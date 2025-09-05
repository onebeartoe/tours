package org.onebeartoe.tours.pdf;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.apache.pdfbox.Loader;

/**
 *
 */
public class ExractImagesFromPdf extends PDFStreamEngine
{
    
// help from this anwser was provided this:
//
//                 https://stackoverflow.com/a/63399933/803890

//    private final String filePath;
//    
    private final String outputDir;
    
    public ExractImagesFromPdf(
//    String filePath, 
    String outputDir)
    {
//        this.filePath = filePath;
        this.outputDir = outputDir;
    }




    @Override
    protected void processOperator(Operator operator, List<COSBase> operands) throws IOException{
        String operation = operator.getName();

        if("Do".equals(operation)){
            COSName objectName = (COSName) operands.get(0);
            PDXObject pdxObject = getResources().getXObject(objectName);

            if(pdxObject instanceof PDImageXObject){
                // Image
                PDImageXObject image = (PDImageXObject) pdxObject;
                BufferedImage bImage = image.getImage();

                // File
                String randomName = UUID.randomUUID().toString();
                File outputFile = new File(outputDir,randomName + ".png");

                // Write image to file
                ImageIO.write(bImage, "PNG", outputFile);

            }else if(pdxObject instanceof PDFormXObject){
                PDFormXObject form = (PDFormXObject) pdxObject;
                showForm(form);
            }
        }

        else super.processOperator(operator, operands);
    }

    public static void main(String[] args)
    {
        String infilePath = "/home/luke/Distribute/world/adafruit/led/letters/M0010-A_Z-25.pdf";
        
        var parent = new File(infilePath).getParentFile();
        
        String outputDir = "target/pdf-images/";
        
        File outputDirecoty = new File(parent, outputDir);
        outputDirecoty.mkdirs();

        ExractImagesFromPdf useCase = new ExractImagesFromPdf(
  //              infilePath,
                outputDir);
        
        try{
            File file = new File(filePath);
            PDDocument document = Loader.loadPDF(file);

            for(PDPage page : document.getPages()){
                processPage(page);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
