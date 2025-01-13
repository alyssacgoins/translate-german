package com.translate.german.translator;

import static com.translate.german.ErrorCodes.PROCESSING_ERROR_001;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Processor {

  static final String COMMA = ",";
  static final String BODY_TEXT_CSV = "body-text.csv";

  Translator translator = new Translator();

  public Map<String, String> process() {
    Map<String, String> translations = new HashMap<>();

    try (BufferedReader br = new BufferedReader(new FileReader(BODY_TEXT_CSV))) {
      String line = br.readLine();

      int counter = 0;
      for (String value : line.split(COMMA)) {
        if (counter>10) {
          break;
        }
        translations.put(value, translator.translate(value));
        counter++;
      }

    } catch (IOException e) {
      log.error(PROCESSING_ERROR_001, e);
    }
    return translations;
  }

}
