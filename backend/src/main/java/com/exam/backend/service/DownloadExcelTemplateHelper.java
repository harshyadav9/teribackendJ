package com.exam.backend.service;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class DownloadExcelTemplateHelper {

    Logger log = LoggerFactory.getLogger(DownloadExcelTemplateHelper.class);

    @Autowired
    public DownloadExcelTemplateHelper() {

    }

    private HSSFWorkbook workbook = new HSSFWorkbook();
    private HSSFSheet sheet;

    public String downloadTemplate(HttpServletResponse response) {
        writeCandidateHeaderLine("StudentUploadTemplate");
        return returnResponseEntity("StudentUploadTemplate.xlsx");

    }

    private void writeCandidateHeaderLine(String sheetName){
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        //HSSFFont font = workbook.createFont();
        //font.setBold(true);
        //font.setFontHeight(10);
        //style.setFont(font);

        createCell(row, 0, "NAME", style);
        createCell(row, 1, "DOB", style);
        createCell(row, 2, "CLASS", style);
        createCell(row, 3, "SECTION", style);
        createCell(row, 4, "EXAM THEME", style);
        createCell(row, 5, "MOCK TEST", style);
    }

    private String returnResponseEntity(String workbookName) {

        String home = System.getProperty("user.home");
        String filename = home + "/Downloads/" + workbookName;

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            log.info("Your excel file has been generated!" + filename);
            System.out.println("Your excel file has been generated!");
            return "Your excel file has been generated in Download folder";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return filename+" is already open. Please close the file and trigger again.";
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

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
