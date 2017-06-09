package com.drachenfrucht1.spielt.webconnector;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Dominik on 09.05.2017.
 */
public class FileUtils {

  public static String getFileContents(String filename, Main main) {
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(main.getDataFolder() + "//webpages//" + filename));
      StringBuilder stringBuilder = new StringBuilder();

      String line = bufferedReader.readLine();

      while(line != null) {
        stringBuilder.append(line);
        stringBuilder.append(System.lineSeparator());
        line = bufferedReader.readLine();
      }

      return stringBuilder.toString();
    } catch (Exception e) {
      return getFileContents("index.html", main);
    }
  }

  public static String getLogContents() {
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader("logs//latest.log"));
      StringBuilder stringBuilder = new StringBuilder();

      String line = bufferedReader.readLine();

      while(line != null) {
        stringBuilder.append(line);
        stringBuilder.append("<br>");
        line = bufferedReader.readLine();
      }

      return stringBuilder.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return "<h1>Error</h1>";
  }
}
