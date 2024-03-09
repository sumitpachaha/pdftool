package com.pdftool;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.*;
import org.apache.pdfbox.contentstream.operator.state.*;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.util.Matrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractDataFromPDF extends PDFStreamEngine {

    public void ExtractDataFromPDF() throws IOException
    {
        addOperator(new Concatenate());
        addOperator(new DrawObject());
        addOperator(new SetGraphicsStateParameters());
        addOperator(new Save());
        addOperator(new Restore());
        addOperator(new SetMatrix());
    }

    String extractPhoneNumberFromPDF(PDDocument doc) throws IOException {
        StringBuilder sb = new StringBuilder();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        sb.append(pdfStripper.getText(doc));
        Pattern pattern = Pattern.compile("\\s\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d\\s");
        Matcher matcher = pattern.matcher(sb);
        while (matcher.find()){
            String phoneNumber= matcher.group();
            System.out.println(phoneNumber);
        }
        return sb.toString();
    }

    String extractTextFromPDF(PDDocument doc) throws IOException {
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String content= pdfStripper.getText(doc);
        System.out.println("Text in the PDF ----------------------");
        System.out.println(content);
        System.out.println("End ----------------------");
        return content;
    }

    void extractImageFromPDF(PDDocument doc) throws IOException {
        int pageNum = 0;
        for(PDPage page:doc.getPages()) {
            PDFRenderer renderer = new PDFRenderer(doc);
            BufferedImage image = renderer.renderImage(pageNum);
            System.out.println( "\n\nProcessing PDF page: " + pageNum +"\n---------------------------------");
            ImageIO.write(image, "JPEG", new File("image_"+pageNum+".jpeg"));
            System.out.println("Image created successfully.");
            pageNum++;
        }
    }
    public void getImageLocation(PDDocument document)throws IOException {

        try
        {
            ExtractDataFromPDF printer = new ExtractDataFromPDF();
            int pageNum = 0;
            for( PDPage page : document.getPages() )
            {
                pageNum++;
                System.out.println( "\n\nProcessing PDF page: " + pageNum +"\n---------------------------------");
                printer.processPage(page);
            }
        }
        finally
        {
            if( document != null )
            {
                document.close();
            }
        }
    }

    @Override
    protected void processOperator( Operator operator, List<COSBase> operands)
            throws IOException
    {
        String operation = operator.getName();
        if( "Do".equals(operation) )
        {
            COSName objectName = (COSName) operands.get( 0 );
            PDXObject xobject = getResources().getXObject( objectName );

            if( xobject instanceof PDImageXObject)
            {
                PDImageXObject image = (PDImageXObject)xobject;
                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();

                System.out.println("\nImage [" + objectName.getName() + "]");

                Matrix ctmNew = getGraphicsState().getCurrentTransformationMatrix();
                float imageXScale = ctmNew.getScalingFactorX();
                float imageYScale = ctmNew.getScalingFactorY();

                System.out.println("position in PDF = " + ctmNew.getTranslateX() + ", " + ctmNew.getTranslateY() + " in user space units");
                System.out.println("raw image size  = " + imageWidth + ", " + imageHeight + " in pixels");
                System.out.println("displayed size  = " + imageXScale + ", " + imageYScale + " in user space units");

            }
            else if(xobject instanceof PDFormXObject)
            {
                PDFormXObject form = (PDFormXObject)xobject;
                showForm(form);
            }
        }
        else
        {
            super.processOperator( operator, operands );
        }
    }
}
