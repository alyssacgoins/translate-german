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

/**
 * Process body-text.csv into translated pairs.
 */
@Slf4j
public class Processor {

  static final String COMMA = ",";
  static final String BODY_TEXT_CSV = "body-text.csv";

  Translator translator = new Translator();

  // English-language dictionary calls (Only EN for now)
  Dictionary englishDictionary = new Dictionary(Language.EN);

  /**
   * Return a map of words retrieved from body-text.csv and their corresponding translations.
   *
   * @return Map<String, String></String,>
   */
  public Map<String, String> getTranslationMapFromCsv() {
    Map<String, String> translations = new HashMap<>();

    try (BufferedReader br = new BufferedReader(new FileReader(BODY_TEXT_CSV))) {
      String line = br.readLine();

      //todo: this is only in place to limit # of DeepL API calls.
      int counter = 0;
      for (String value : line.split(COMMA)) {
        if (counter > 100) {
          break;
        }
        String translation = translator.translate(value);

        // If the initial word is English, ignore this pair.
        if (translation.equalsIgnoreCase(value) && englishDictionary.isLang(value)) {
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

}
