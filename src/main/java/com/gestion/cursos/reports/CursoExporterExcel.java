package com.gestion.cursos.reports;

import com.gestion.cursos.entities.Curso;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.store.Cur;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.List;

public class CursoExporterExcel {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Curso> cursos;
    public CursoExporterExcel(List<Curso> cursos){
        this.cursos = cursos;
        workbook = new XSSFWorkbook();
    }
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Cursos");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell (row, 0, "ID", style);
        createCell(row, 1, "Título", style);
        createCell(row, 2, "Descripción", style);
        createCell(row, 3, "Nivel", style);
        createCell(row, 4, "Publicado", style);
    }

    private  void createCell(Row row, int columCount,Object value,CellStyle style){
        sheet.autoSizeColumn(columCount);
        Cell cell = row.createCell(columCount);
        if (value instanceof Integer){
            cell.setCellValue((Integer) value);
        }else if (value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }else{
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    private void writeDataLines(){
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Curso curso:cursos){
            Row row = sheet.createRow(rowCount++);
            int columCount = 0;
            createCell(row,columCount++,curso.getId(),style);
            createCell(row,columCount++,curso.getTitulo(),style);
            createCell(row,columCount++,curso.getDescripcion(),style);
            createCell(row,columCount++,curso.getNivel(),style);
            createCell(row,columCount++,curso.isPublicado(),style);
        }
    }

    public void export(HttpServletResponse response) throws IOException{
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
