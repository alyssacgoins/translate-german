package com.translate.german.translate;

import static com.translate.german.ErrorCodes.PROCESSING_ERROR_001;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class Processor {

  static final String COMMA = ",";
  static final String BODY_TEXT_CSV = "body-text.csv";

  Translator translator;

  /**
   * Return map of English/German translations
   *
   * @return map
   */
  public Map<String, String> process() {
    Map<String, String> translations = new HashMap<>();

    try (BufferedReader br = new BufferedReader(new FileReader(BODY_TEXT_CSV))) {
      String line = br.readLine();

      int counter = 0;
      for (String word : line.split(COMMA)) {
        if (counter > 10) {
          break;
        }
        translations.put(word, translator.translate(word));
        counter++;
      }

    } catch (IOException e) {
      log.error(PROCESSING_ERROR_001, e);
    }
    return translations;
  }

}
