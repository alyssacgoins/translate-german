package com.translate.german;

import com.translate.german.excel.ExcelGenerator;
import com.translate.german.translator.Processor;

import lombok.extern.slf4j.Slf4j;

/**
 * Driver class for translate-german program.
 */
@Slf4j
public class Main {

  public static void main(String[] args) {

    Processor processor = new Processor();
    ExcelGenerator generator = new ExcelGenerator();
    generator.generateExcelFromMap(processor.getTranslationMapFromCsv());
  }
}