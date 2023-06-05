package com.example.foryou.DAO.Entities;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
public class PdfCredit {
    public void generate(Credit  credit, HttpServletResponse response) throws DocumentException, IOException{
        // Creating the Object of Document
        Document document = new Document(PageSize.A4);
        // Getting instance of PdfWriter
        PdfWriter.getInstance(document, response.getOutputStream());
        // Opening the created document to change it
        document.open();


        Image logo = Image.getInstance("image/logo1.png"); // Replace with path to your logo image
        logo.scaleToFit(100, 60); // Resize image to fit within 100x100 pixels
        Chunk logoChunk = new Chunk(logo, 0, -20, false);
        Phrase logoPhrase = new Phrase(logoChunk);
        HeaderFooter header = new HeaderFooter(logoPhrase, false);
        document.setHeader(header);
        document.add(logo);

        Paragraph paragraph0 = new Paragraph("ForYou Insurance");
        paragraph0.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(paragraph0);
        // Creating font
        // Setting font style and size
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);
        // Creating paragraph
        Paragraph paragraph1 = new Paragraph("Credit Notification", fontTitle);
        // Aligning the paragraph in the document
        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
        // Adding the created paragraph in the document
        document.add(paragraph1);

        Paragraph paragraph3 = new Paragraph("\n "+"\n "+"Dear Sir/Madam," + "\n "+
                "The credit details are as follows.");
        // Aligning the paragraph in the document
        paragraph3.setAlignment(Paragraph.ALIGN_LEFT);
        // Adding the created paragraph in the document
        document.add(paragraph3);



        // Creating a table of the 4 columns
        PdfPTable table = new PdfPTable(2);
        // Setting width of the table, its columns and spacing
        table.setWidthPercentage(100f);
        table.setWidths(new int[] {1,3});
        table.setSpacingBefore(10);
        // Create Table Cells for the table header
        PdfPCell cell = new PdfPCell();

        // Setting the background color and padding of the table cell
        cell.setBackgroundColor(CMYKColor.BLUE);
        cell.setPadding(5);
        // Creating font
        // Setting font style and size
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(CMYKColor.WHITE);




        // Adding headings in the created table cell or  header
        // Adding Cell to table
        cell.setPhrase(new Phrase("Credit_ID", font));
        table.addCell(cell);
        table.addCell(String.valueOf(credit.getCreditId()));

        cell.setPhrase(new Phrase("Client", font));
        table.addCell(cell);
        table.addCell(credit.getClient().getFirstName());

        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);
        table.addCell(String.valueOf(credit.getAmount()));

        cell.setPhrase(new Phrase("RefundAmount", font));
        table.addCell(cell);
        table.addCell(String.valueOf(credit.getRefundAmount()));

        cell.setPhrase(new Phrase("Start Date", font));
        table.addCell(cell);
        table.addCell(String.valueOf(credit.getStartDate()));

        cell.setPhrase(new Phrase("End Date", font));
        table.addCell(cell);
        table.addCell(String.valueOf(credit.getEndtDate()));




        // Adding the created table to the document
        document.add(table);
        Font fontTitle1 = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle1.setSize(14);
        Paragraph paragraph9 = new Paragraph( "\n "+"Important Notes", fontTitle1);
        paragraph9.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(paragraph9);

        Paragraph paragraph5 = new Paragraph("This is a notification that we received instruction to effect a payment and not a representation " +
                "of any kind of guaranteethat the amount has in fact been transfered or shall be available in the account . " +
                "The processing of the payment may be delayed which impact of the timing of the availability of th funds.");
        paragraph5.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(paragraph5);

        Paragraph paragraph6 = new Paragraph( "\n "+"\n "+"\n "+"\n "+"\n "+"Page 1 of 1");
        paragraph6.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(paragraph6);

        // Closing the document
        document.close();
    }
}