package com.translate.german.translator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.extern.slf4j.Slf4j;

/**
 * Retrieves translation of given words/set of words using DeepL API.
 */
@Slf4j
public class Translator {

  static final String AUTH_HEADER = "Authorization";
  static final String CONTENT_HEADER = "Content-Type";
  static final String APPLICATION_JSON = "\"application/json\"";
  static final String DEEPL_TRANSLATE_URL = "https://api-free.deepl.com/v2/translate";

  static final String DEEPL_AUTH_KEY = "Deepl-Auth-Key";
  static final String SPACE = " ";
  static final String EMPTY_STRING = "";

  public String translate(String input) {

    String post = "{ \"text\": [\"" + input + "\"],\"target_lang\": \"EN\"}";
    String output = EMPTY_STRING;
    try {
      HttpClient client = HttpClient.newBuilder().build();

      HttpRequest request = HttpRequest.newBuilder().uri(new URI(DEEPL_TRANSLATE_URL))
          .header("Authorization", "DeepL-Auth-Key 6bf220ba-bb0a-463b-8262-b43a761d2b73:fx")
          .header("Content-Type", "application/json").POST(BodyPublishers.ofString(post)).build();
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
      output = response.body();
      System.out.println(output);
    } catch (URISyntaxException | IOException | InterruptedException e) {
      log.error("InterruptedException ", e);
    }
    return output;
  }
}
