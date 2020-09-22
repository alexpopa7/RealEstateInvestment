package com.proiect.licentam.utils;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.proiect.licentam.model.Home;

import java.io.FileOutputStream;
import java.util.List;

public class ExportPdf {

    private static String FILE = "C:\\Users\\Alex\\Desktop\\Homes.pdf";

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);


    public boolean exportPDf (List<Home> homeList){
        boolean succes = false;
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            addContent(document ,homeList);
            document.close();
            succes = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(succes) {
            return true;
        }
        return false;
    }

    private static void addMetaData(Document document) {
        document.addTitle("Exported Homes");
        document.addSubject("Homes");
    }

    private void addContent(Document document, List<Home> homeList) throws DocumentException {
        Anchor anchor = new Anchor("Homes exported from Real Estate Investment App", subFont);
        anchor.setName("The file with your needed homes");

        Chapter catPart = new Chapter(new Paragraph(anchor),1);
        catPart.add(new Paragraph(" "));
        createTable(catPart, homeList);
        document.add(catPart);
    }

    private static void createTable(Chapter chapter, List<Home> homeList)
            throws BadElementException {
        PdfPTable table = new PdfPTable(7);

        PdfPCell c1 = new PdfPCell(new Phrase("Type"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Price"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Surface"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Year built"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Zone Type"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Adress"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Status"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);


        for(Home h: homeList){
            table.addCell(h.getType());
            table.addCell(h.getPrice().toString() + " " + "€");
            table.addCell(h.getConstructedArea().toString() + " " + "m^2");
            table.addCell(h.getBuildYear().toString());
            table.addCell(h.getZoneType());
            table.addCell(h.getAddress() + " " + h.getNumber());
            table.addCell(h.getStatus());
        }
        chapter.add(table);
    }
}
