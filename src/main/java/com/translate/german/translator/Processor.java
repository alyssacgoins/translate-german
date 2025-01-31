package com.translate.german.translator;

import static com.translate.german.ErrorCodes.PROCESSING_ERROR_001;

import com.translate.german.constants.Language;
import com.translate.german.dictionaries.Dictionary;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Processor {

  static final String COMMA = ",";
  static final String BODY_TEXT_CSV = "body-text.csv";

  Translator translator = new Translator();

  // English-language dictionary calls (Only EN for now)
  Dictionary dictionary = new Dictionary();

  public Map<String, String> process() {
    Map<String, String> translations = new HashMap<>();

    try (BufferedReader br = new BufferedReader(new FileReader(BODY_TEXT_CSV))) {
      String line = br.readLine();

      int counter = 0;
      for (String value : line.split(COMMA)) {
        if (counter>10) {
          break;
        }
        String translation = translator.translate(value);

        // If the initial word is English, ignore this pair.
        if (translation.equalsIgnoreCase(value) && dictionary.isLang(value, Language.EN)) {
          continue;
        }
        translations.put(value, translator.translate(value));
        counter++;
      }

    } catch (IOException e) {
      log.error(PROCESSING_ERROR_001, e);
    }
    return translations;
  }

  public boolean isIdentical(String word, String translation) {
    return true;
  }

}
