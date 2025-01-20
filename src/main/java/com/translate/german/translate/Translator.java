package com.translate.german.translate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;
import java.util.Properties;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Translates given words/set of words
 */
@Slf4j
public class Translator {

  private static final String AUTH_HEADER = "Authorization";
  private static final String CONTENT_HEADER = "Content-Type";
  private static final String APPLICATION_JSON = "application/json";
  private static final String DEEPL_TRANSLATE_URL = "https://api-free.deepl.com/v2/translate";
  private static final String DEEPL_AUTH_KEY = "DeepL-Auth-Key";
  private static final String APPLICATION_PROPERTIES_PATH = "src/main/resources/application.properties";

  private static final String SPACE = " ";
  private static final String EMPTY_STRING = "";


  private static final String TRANSLATIONS = "translations";
  private static final String TEXT = "text";

  private static final int ZERO = 0;

  /**
   * Return input string translated to German.
   *
   * @param input String (English word)
   * @return String
   */
  public String translate(final String input) {

    String output = EMPTY_STRING;

    try {
      HttpClient client = HttpClient.newBuilder().build();

      HttpRequest request = HttpRequest.newBuilder()
          .uri(new URI(DEEPL_TRANSLATE_URL))
          .header(AUTH_HEADER, DEEPL_AUTH_KEY + SPACE + getDeeplAuthKey())
          .header(CONTENT_HEADER, APPLICATION_JSON)
          .POST(BodyPublishers.ofString(createDeepLPostBody(input)))
          .build();

      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
      Optional<String> translation = getTranslationFromJsonResponse(response.body());

      if (translation.isPresent()) {
        output = translation.get();
      }

      System.out.println(output);
    } catch (URISyntaxException | IOException | InterruptedException e) {
      log.error("InterruptedException ", e);
    }
    return output;
  }

  /**
   * Return DeepL Translate API authorization key, retrieved from application.properties
   *
   * @return String
   */
  private String getDeeplAuthKey() {
    String key = EMPTY_STRING;
    Properties props = new Properties();
    try {
      props.load(new FileInputStream(APPLICATION_PROPERTIES_PATH));
      key = props.getProperty("deepl_key");

    } catch (IOException e) {
      log.error("Error", e);
    }


    return key;
  }

  /**
   * Return body of DeepL Translate POST request, containing input word to be translated.
   *
   * @param inputWord String
   * @return String
   */
  private String createDeepLPostBody(final String inputWord) {
    return "{ \"text\": [\"" + inputWord + "\"],\"target_lang\": \"EN\"}";
  }

  /**
   * Return translated word from input json response.
   *
   * @param jsonResponse String json response
   * @return String
   */
  private Optional<String> getTranslationFromJsonResponse(final String jsonResponse) {
    Optional<String> translation = Optional.empty();
    JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

    if (jsonObject != null && isFormattedCorrectly(jsonObject)) {
      translation = Optional.of(jsonObject.get(TRANSLATIONS).getAsJsonArray().get(ZERO)
          .getAsJsonObject().get(TEXT).getAsString());
    }
    return translation;
  }

  /**
   * Return true if input json object contains "translations" and "text" member entries.
   *
   * @param json JsonObject
   * @return boolean
   */
  private boolean isFormattedCorrectly(final JsonObject json) {
    return containsKey(json, TRANSLATIONS) &&
        containsKey(json.get(TRANSLATIONS).getAsJsonArray().get(ZERO).getAsJsonObject(), TEXT);
}

  /**
   * Return true if input json object contains input key.
   *
   * @param object JsonObject
   * @param key String
   * @return boolean
   */
  private boolean containsKey(@NonNull final JsonObject object, final String key) {
    return object.keySet().contains(key);
  }
}
