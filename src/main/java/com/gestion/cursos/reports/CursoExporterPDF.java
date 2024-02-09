package com.gestion.cursos.reports;

import com.gestion.cursos.entities.Curso;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CursoExporterPDF {
    private List<Curso> listarCurso;
    public CursoExporterPDF(List<Curso>listarCurso){
        this.listarCurso = listarCurso;
    }
    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLACK);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
        cell.setPhrase(new Phrase("ID",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Titulo",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Descripcion",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Nivel",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Publicado",font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Curso curso : listarCurso) {
            table.addCell(String.valueOf(curso.getId()));
            table.addCell(curso.getTitulo());
            table.addCell(curso.getDescripcion());
            table.addCell(String.valueOf(curso.getNivel()));
            table.addCell(String.valueOf(curso.isPublicado()));
        }


    }
    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("Lista de cursos", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.3f, 3.5f, 3.5f, 2.0f, 1.5f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
    }
}

