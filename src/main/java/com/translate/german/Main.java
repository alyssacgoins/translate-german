package com.translate.german;

import com.translate.german.excel.ExcelGenerator;
import com.translate.german.translate.Processor;
import com.translate.german.translate.Translator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(String[] args) {
    System.out.println("Hallo, Welt!");

    Translator translator = new Translator();
    Processor processor = new Processor(translator);
    ExcelGenerator generator = new ExcelGenerator();
    generator.generateExcelFromMap(processor.process());
  }
}