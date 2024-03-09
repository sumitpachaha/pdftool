package com.pdftool;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.IOException;
import java.util.List;

public class FillPDFForm {

    void fillApplicationFormInPDF(PDDocument document) throws IOException {
        PDDocumentCatalog pc = document.getDocumentCatalog();
        PDAcroForm acroForm = pc.getAcroForm();
        List<PDField> fields=	acroForm.getFields();
        for (PDField f : fields ) {
            System.out.println(f.getFullyQualifiedName());
            if(f.getFullyQualifiedName().equals("name2[first]")) {
                if (f.getFieldType().equals("Tx")) {
                    f.setValue("firstname");
                    System.out.print(" firstname\n");
                }
            }
            else if(f.getFullyQualifiedName().equals("name2[last]")) {
                if (f.getFieldType().equals("Tx")) {
                    f.setValue("lastname");
                    System.out.print(" lastname\n");
                }
            }
            else if(f.getFullyQualifiedName().equals("email3")) {
                if (f.getFieldType().equals("Tx")) {
                    f.setValue("email@gmail.com");
                    System.out.print(" email@gmail.com\n");
                }
            }
            else if(f.getFullyQualifiedName().equals("address4[addr_line1]")) {
                if (f.getFieldType().equals("Tx")) {
                    f.setValue("addressline1");
                    System.out.print(" addressline1\n");
                }
            }
            else if(f.getFullyQualifiedName().equals("address4[addr_line2]")) {
                if (f.getFieldType().equals("Tx")) {
                    f.setValue("addressline2");
                    System.out.print(" addressline2\n");
                }
            }
            else if(f.getFullyQualifiedName().equals("address4[city]")) {
                if (f.getFieldType().equals("Tx")) {
                    f.setValue("city");
                    System.out.print(" city\n");
                }
            }
            else if(f.getFullyQualifiedName().equals("address4[state]")) {
                if (f.getFieldType().equals("Tx")) {
                    f.setValue("state");
                    System.out.print(" state\n");
                }
            }
            else if(f.getFullyQualifiedName().equals("address4[postal]")) {
                if (f.getFieldType().equals("Tx")) {
                    f.setValue("0000");
                    System.out.print(" 0000\n");
                }
            }
            else if(f.getFullyQualifiedName().equals("phoneNumber5[area]")) {
                if (f.getFieldType().equals("Tx")) {
                    f.setValue("+1");
                    System.out.print(" +1\n");
                }
            }
            else if(f.getFullyQualifiedName().equals("phoneNumber5[phone]")) {
                if (f.getFieldType().equals("Tx")) {
                    f.setValue("90000000");
                    System.out.print(" 90000000\n");
                }
            }
        }
        System.out.println(" Form Filled !!\n");
        document.save("PDFForm_modified.pdf");
    }
}
