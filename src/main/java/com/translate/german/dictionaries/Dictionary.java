package com.translate.german.dictionaries;

import static com.translate.german.constants.Language.EN;

import com.google.api.client.http.HttpStatusCodes;
import com.translate.german.constants.Language;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Dictionary {

  /**
   * Return true if input word is English.
   *
   * @param word String word
   * @return boolean
   */
  public boolean isLang(String word, Language lang) {
    boolean isLang = false;
    switch (lang) {
      case EN:
        isLang = isEnglishWord(word);
        break;
      case DE:
        //isLang = getGermanEntry(word).isPresent();
        break;
      default:
        // todo add specific error code.
        log.error("Language {} not currently supported.", lang);
    }
    return isLang;
  }

  public boolean isEnglishWord(String word) {
    boolean isWord = false;

    HttpResponse<String>  result = sendDictionaryRequest(word, EN);
    if (result.statusCode() == HttpStatusCodes.STATUS_CODE_OK) {
     isWord = true;
    }

    return isWord;
  }

  public HttpResponse<String> sendDictionaryRequest(String word, Language lang) {
    //todo find better way to do this
    HttpResponse<String> response = null;
    try {
      String url =
          "https://api.dictionaryapi.dev/api/v2/entries/" + lang.toString().toLowerCase() + "/" + word;

      HttpClient client = HttpClient.newBuilder().build();
      HttpRequest req = HttpRequest.newBuilder()
          .uri(new URI(url))
          .build();
      response = client.send(req, BodyHandlers.ofString());

    } catch (URISyntaxException e) {
      // todo add specific error code.
      log.error("Error connecting to English-language dictionary.");
    } catch (IOException | InterruptedException e) {
      // todo add specific error code.
      log.error("Error connecting to English-language dictionary 2.");
    }
    return response;
  }

}
