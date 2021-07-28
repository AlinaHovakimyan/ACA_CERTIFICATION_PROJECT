package common;

import instances.Course;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

/**
 * Creates pdf file
 */
public class PdfHelper {
    public static PDDocument createPDF(String applicantname, Course course) {
        try {
        final PDDocument document = new PDDocument();
        final PDPage page = new PDPage();
        document.addPage(page);
        final PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN);
        contentStream.newLineAtOffset(25, 700);
        contentStream.showText("CERTIFICATE");
        contentStream.newLine();
        contentStream.showText(course.getName());
        contentStream.newLine();
        contentStream.showText(course.getDescription());
        contentStream.newLine();
        contentStream.showText("Applicant name - " + applicantname);
        contentStream.newLine();
        contentStream.showText("Teacher name - " + course.getTeacher());
        contentStream.close();
        return document;
    } catch (
    IOException ex) {
        throw new RuntimeException(ex);
    }
    }
}
