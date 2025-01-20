package com.translate.german.excel;

import static com.translate.german.ErrorCodes.EXCEL_GENERATOR_ERROR_001;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Return Excel file containing input German/English translation pairs.
 */
@Slf4j
public class ExcelGenerator {

  static final String OUTPUT_EXCEL_FILE_NAME = "output.xlsx";

  static final int ZERO = 0;
  static final int ONE = 1;
  /**
   * Write Excel file output.xlsx containing German/English translations, from input mapped
   *  translations.
   *
   * @param map German/English translations
   */
  public void generateExcelFromMap(Map<String, String> map) {
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet();

    int rowNum = 0;
    for (Map.Entry<String, String> entry : map.entrySet()) {
      if (rowNum > 100) {
        break;
      }
      XSSFRow row = sheet.createRow(rowNum);

      XSSFCell cellEN = row.createCell(ZERO);
      cellEN.setCellValue(entry.getKey());

      XSSFCell cellDE = row.createCell(ONE);
      cellDE.setCellValue(entry.getValue());

      rowNum ++;
    }

    try (FileOutputStream out = new FileOutputStream((OUTPUT_EXCEL_FILE_NAME))) {
      workbook.write(out);
      workbook.close();
    } catch (IOException e) {
      log.error(EXCEL_GENERATOR_ERROR_001, e);
    }
  }
}
