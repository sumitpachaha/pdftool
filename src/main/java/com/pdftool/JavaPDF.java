package com.pdftool;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

public class JavaPDF {
    public static void main(String[] args) {
        PDDocument doc = null;
        Scanner sc = new Scanner(System.in);
        JavaPDF pdf = new JavaPDF();
        String fileLoc = null;
        try{
            System.out.println("Welcome to PDF tool");
            System.out.println("Please enter your choice");
            System.out.println("Press 1 : Create new Empty PDF");
            System.out.println("Press 2 : Create new Hello World Example PDF");
            System.out.println("Press 3 : Fill PDF Form");
            System.out.println("Press 10 : Exit");
            int input = Integer.parseInt(sc.next());
                try {
                    switch (input) {
                        case 1:
                            System.out.println("Please enter File Location --  for example ('C:\\Users\\sumit\\javapdf\\empty.pdf')");
                            fileLoc = sc.next();
                            pdf.createEmptyPDF(fileLoc);
                            break;
                        case 2:
                            System.out.println("Please enter File Location --  for example ('C:\\Users\\sumit\\javapdf\\pdfBoxHelloWorld.pdf')");
                            fileLoc = sc.next();
                            pdf.createNewPdfWithHelloWorld(fileLoc);
                            break;
                        case 3:
                            System.out.println("Please enter File Location --  for example ('C:\\Users\\sumit\\javapdf\\PDFForm.pdf')");
                            FillPDFForm form = new FillPDFForm();
                            File file = new File("PDFForm.pdf");
                            doc = PDDocument.load(file);
                            System.out.println("PDF loaded !!!!");
                            form.fillApplicationFormInPDF(doc);
                            break;
                        case 10:
                            break;
                        default:
                            System.out.println("Invalid Input !! Please enter correct choice");
                    }
                } catch (NumberFormatException ne) {
                    System.out.println("Invalid Input !! Please enter number");
                }
            /*System.out.println("PDF loaded !!!!");
            File file = new File("PDFForm.pdf");
            doc = PDDocument.load(file);
            System.out.println("PDF loaded !!!!");
            int noOfPages = doc.getNumberOfPages();
            System.out.println("PDF has "+noOfPages+" pages !!!!");
            new FillPDFForm().fillApplicationFormInPDF(doc);
            //PDPage page = doc.getPage(0);
            //doc.removePage(0); #to remove page

            //Closing the document
            doc.close();*/

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    void createEmptyPDF(String fileLocation) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        document.save(fileLocation);
        document.close();
        System.out.println("PDF created !!!!");
    }

    void createEmptyPdfWithMultiplePage(String fileLocation, int numPages) throws IOException {
        PDDocument document = new PDDocument();
        for (int i = 1;i<=numPages;i++){
            PDPage page = new PDPage();
            document.addPage(page);
        }
        document.save(fileLocation);
        document.close();
        System.out.println("PDF created !!!!");
    }

        void createNewPdfWithHelloWorld(String fileLocation) {
            try {
                PDDocument document = new PDDocument();
                PDPage page1 = new PDPage();
                document.addPage(page1);

                PDPageContentStream contentStream = new PDPageContentStream(document, page1);
                PDFont font = PDType1Font.HELVETICA_BOLD;
                contentStream.setFont(font, 20);
                contentStream.beginText();
                contentStream.newLineAtOffset(20, 500);
                contentStream.setLeading(20f);
                contentStream.showText("Hello World");
                contentStream.newLine();
                contentStream.showText("This is generated using JAVA PDF tool application !");
                contentStream.newLine();
                contentStream.endText();
                contentStream.close();

                PDImageXObject pdImage = PDImageXObject.createFromFile("hello.jpg",document);

                PDPage page2 = new PDPage();
                document.addPage(page2);
                contentStream = new PDPageContentStream(document, page2);
                contentStream.drawImage(pdImage,20,200);
                contentStream.close();


                document.save(fileLocation);
                document.close();
                System.out.println("PDF created !!!!");
            }catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
    }