package com.pdftool;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PDFMetadata {
    public PDFMetadata(){

    }
    protected boolean setMetadataForPDF(PDDocument document, String author, String title, String creator, String subject, String producer) {
        boolean isSet = false;
        try {
            PDDocumentInformation pddInform = document.getDocumentInformation();
            pddInform.setAuthor(author);
            pddInform.setTitle(title);
            pddInform.setCreator(creator);
            pddInform.setSubject(subject);
            pddInform.setKeywords("Java, example, my pdf");
            pddInform.setProducer(producer);

            Calendar date = new GregorianCalendar();
            date.set(2024, 3, 9);
            pddInform.setCreationDate(date);
            date.set(2024, 3, 9);
            pddInform.setModificationDate(date);
        }catch (Exception ex){
            ex.printStackTrace();
            return isSet;
        }
        isSet = true;
        return isSet;
    }

    protected boolean setModificationDate(PDDocument document){
        boolean isSet = false;
        PDDocumentInformation pddInform = document.getDocumentInformation();
        Calendar date = new GregorianCalendar();
        pddInform.setModificationDate(date);
        return isSet;
    }
}
