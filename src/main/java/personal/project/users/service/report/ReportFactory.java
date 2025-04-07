package personal.project.users.service.report;

import lombok.Builder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

// Responsável por gerar um relatório em Excel (.xls) usando Apache POI.

@Builder
public class ReportFactory {
    private String name;
    private List<String> headers;
    private List<List<String>> records;

    // é uma classe abstrata que permite ler dados em bytes a partir de uma fonte,
    // como um arquivo, uma conexão de rede ou uma matriz de bytes
    public InputStream generate() throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet(name);
            writeHeader(sheet);
            write(sheet);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            workbook.write(output);
            return new ByteArrayInputStream(output.toByteArray());
        }
    }

    private void writeHeader(XSSFSheet sheet) {

        Row row = sheet.createRow(0);

        CellStyle style = sheet.getWorkbook().createCellStyle();
        XSSFFont font = sheet.getWorkbook().createFont();
        font.setBold(true);
        //font.setColor("white");
        font.setFontHeight(14);
        style.setFont(font);
        for (int i = 0; i < headers.size(); i++) {
            createCell(row, i, headers.get(i), style);
        }

    }

    private void write(XSSFSheet sheet) {
        CellStyle style = sheet.getWorkbook().createCellStyle();
        XSSFFont font = sheet.getWorkbook().createFont();
        font.setFontHeight(14);
        style.setFont(font);
        int rowCount = 0;

        Iterator<List<String>> it = records.iterator();
        while (it.hasNext()) {
            List<String> rowList = it.next();
            Row row = sheet.createRow(++rowCount);
            for (int j = 0; j < headers.size(); j++) {
                createCell(row, j, rowList.get(j), style);
            }
        }

        for (int i = 0; i < headers.size(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        cell.setCellValue((String) value);
        cell.setCellStyle(style);
    }
}
