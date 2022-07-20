package com.exam.backend.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class DownloadExcelTemplateHelper {

    Logger log = LoggerFactory.getLogger(DownloadExcelTemplateHelper.class);

    @Autowired
    public DownloadExcelTemplateHelper() {

    }

    XSSFWorkbook workbook ;
    XSSFSheet sheet ;

    public ByteArrayInputStream downloadTemplate() {
        try {
            return writeCandidateHeaderLine("StudentUploadTemplate");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private ByteArrayInputStream writeCandidateHeaderLine(String sheetName) throws IOException {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "NAME", style);
        createCell(row, 1, "DOB", style);
        createCell(row, 2, "CLASS", style);
        createCell(row, 3, "SECTION", style);
        createCell(row, 4, "EXAM THEME", style);
        createCell(row, 5, "MOCK TEST", style);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

   /* private String returnResponseEntity(String workbookName) {

        String home = System.getProperty("user.home");
        String filename = home + "/Downloads/" + workbookName;

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(filename);
            workbookName.write(fileOut);
            fileOut.close();
            workbook.close();
            log.info("Your excel file has been generated!" + filename);
            System.out.println("Your excel file has been generated!");
            return filename;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return filename + " is already open. Please close the file and trigger again.";
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }*/

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

}
