package com.unla.productos_service.util;


import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.unla.productos_service.model.Catalogo;
import com.unla.productos_service.model.Product;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfExportService {

    public byte[] exportCatalogoToPdf(Catalogo catalogo) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        document.add(new Paragraph("Catalogo: " + catalogo.getNombre()));
        document.add(new Paragraph("ID: " + catalogo.getId()));

        // Crear tabla para productos con 5 columnas: ID, Nombre, Código, Color, Talle
        Table table = new Table(5);
        table.addCell("ID");
        table.addCell("Nombre");
        table.addCell("Código");
        table.addCell("Color");
        table.addCell("Talle");

        // Agregar filas de productos
        for (Product product : catalogo.getProducts()) {
            table.addCell(String.valueOf(product.getId()));
            table.addCell(product.getNombre());
            table.addCell(product.getCodigo());
            table.addCell(product.getColor());
            table.addCell(product.getTalle());
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