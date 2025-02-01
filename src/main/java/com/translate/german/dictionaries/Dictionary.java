package com.translate.german.dictionaries;

import com.google.api.client.http.HttpStatusCodes;
import com.translate.german.constants.Language;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Executes dictionary API requests against input words.
 */
@Slf4j
@AllArgsConstructor
public class Dictionary {

  Language lang;

  /**
   * Return true if input word is in input language.
   *
   * @param word String word (e.g. "hello")
   * @return boolean
   */
  public boolean isLang(String word) {
    boolean isLang = false;
    switch (lang) {
      case EN:
        isLang = isEnglishWord(word);
        break;
      case DE:
        // ToDo: build this out
        break;
      default:
        // ToDo: add specific error code.
        log.error("Language {} not currently supported.", lang);
    }
    return isLang;
  }

  /**
   * Return true if input word is an English word.
   *
   * @param word String
   * @return boolean
   */
  public boolean isEnglishWord(String word) {
    boolean isWord = false;
    // ToDo: utilize caching here, so we do not need to send repeat requests.
    HttpResponse<String> result = sendDictionaryRequest(word);
    if (result.statusCode() == HttpStatusCodes.STATUS_CODE_OK) {
      isWord = true;
    }

    return isWord;
  }

  /**
   * Return the HttpResponse
   *
   * @param word String
   * @return HttpResponse<String></String>
   */
  public HttpResponse<String> sendDictionaryRequest(String word) {
    //ToDo: find better way to do this
    HttpResponse<String> response = null;
    try {
      String url =
          "https://api.dictionaryapi.dev/api/v2/entries/" + lang.toString().toLowerCase() + "/"
              + word;

      HttpClient client = HttpClient.newBuilder().build();
      HttpRequest req = HttpRequest.newBuilder().uri(new URI(url)).build();
      response = client.send(req, BodyHandlers.ofString());

    } catch (URISyntaxException e) {
      // ToDo: add specific error code.
      log.error("Error connecting to English-language dictionary.");
    } catch (IOException | InterruptedException e) {
      // ToDo: add specific error code.
      log.error("Error connecting to English-language dictionary 2.");
    }
    return response;
  }

}
