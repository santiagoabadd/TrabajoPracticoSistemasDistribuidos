package com.unla.productos_service.util;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.unla.productos_service.model.Catalogo;
import com.unla.productos_service.model.Product;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

@Service
public class PdfExportService {

    public byte[] exportCatalogoToPdf(Catalogo catalogo) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        document.add(new Paragraph("Catalogo: " + catalogo.getNombre()));
        document.add(new Paragraph("ID: " + catalogo.getId()));


        Table table = new Table(6);
        table.addCell("ID");
        table.addCell("Nombre");
        table.addCell("Código");
        table.addCell("Color");
        table.addCell("Talle");
        table.addCell("Foto");


        for (Product product : catalogo.getProducts()) {
            table.addCell(String.valueOf(product.getId()));
            table.addCell(product.getNombre());
            table.addCell(product.getCodigo());
            table.addCell(product.getColor());
            table.addCell(product.getTalle());
            try {

                String imageUrl = product.getFoto();
                Image image = Image.getInstance(new URL(imageUrl));
                image.scaleToFit(90, 90);
                Cell imageCell = new Cell(image);
                imageCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                table.addCell(imageCell);
            } catch (Exception e) {
                e.printStackTrace();
                table.addCell("Error al cargar imagen");
            }
        }

        document.add(table);
        document.close();

        try (FileOutputStream fileOutputStream = new FileOutputStream("catalogo_" + catalogo.getId() + ".pdf")) {
            byteArrayOutputStream.writeTo(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }
}