package com.baziaka.utils;

import com.baziaka.domain.Ticket;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.OutputStream;


public class PDFGenerator {

    static int FONT_SIZE_SMALL = 16;
    static int FONT_SIZE_BIG = 28;

    public static void generatePdf(OutputStream outputStream, Ticket ticket) throws DocumentException {

        Document document = new Document();

        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN,
                FONT_SIZE_BIG, Font.BOLD);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN,
                FONT_SIZE_SMALL, Font.ITALIC);

        PdfWriter.getInstance(document,
                outputStream);

        document.open();

        Paragraph title = new Paragraph(ticket.getEvent().getCinema().getName(), font1);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(FONT_SIZE_BIG);
        document.add(title);

        document.add(new Paragraph("Auditorium - " + ticket.getEvent().getAuditorium().getName(), font2));

        document.add(new Paragraph("Seat - " + ticket.getSeat().getNumber(), font2));

        document.add(new Paragraph("Price - " + ticket.getPrice(), font2));

        document.add(new Paragraph("Date and time - " +
                ticket.getDate().toString("dd-mm-yyyy HH:mm"), font2));

        document.close();
    }
}
