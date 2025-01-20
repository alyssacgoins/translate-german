package com.translate.german;

/**
 * Error-Handling Codes
 */
public class ErrorCodes {

  // Processor error codes
  public static final String PROCESSOR_ERROR_001 = "Error reading body text csv.";

  // Translator error codes
  public static final String TRANSLATOR_RUNTIME_EXCEPTION = "Error accessing DeepL API. Exiting program.";
  public static final String TRANSLATOR_ERROR_001 = "Error generating DeepL Translate API URL.";
  public static final String TRANSLATOR_ERROR_002 = "Unable to retrieve DeepL API key from application.properties";

  // ExcelGenerator error codes
  public static final String EXCEL_GENERATOR_ERROR_001 = "Error opening Excel file.";

}
