package com.translate.german;

import static com.translate.german.constants.Constants.SAMPLE_GERMAN_TEXT;

import com.translate.german.excel.ExcelGenerator;
import com.translate.german.translator.Processor;
import java.io.File;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(String[] args)
      throws URISyntaxException, IOException, InterruptedException {
    System.out.println("Hallo, Welt!");

    // take in file name
    File file = new File(SAMPLE_GERMAN_TEXT);
    log.info("Loaded {} file", SAMPLE_GERMAN_TEXT);
    HttpClient client = HttpClient.newBuilder().build();

    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://api-free.deepl.com/v2/translate"))
        .header("Authorization", "DeepL-Auth-Key 6bf220ba-bb0a-463b-8262-b43a761d2b73:fx")
        .header("Content-Type", "application/json")
        .POST(BodyPublishers.ofString("{ \"text\": [\"Hello, world!\"],\"target_lang\": \"DE\"}"))
        .build();

    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
    System.out.println(response.body());

    Processor processor = new Processor();
    ExcelGenerator generator = new ExcelGenerator();
    generator.generateExcelFromMap(processor.process());
  }
}