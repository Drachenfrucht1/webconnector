package com.drachenfrucht1.spielt.webconnector.misc;

import com.drachenfrucht1.spielt.webconnector.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

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

  public static String getPlayerList() {
    String response = "<p>";

    for(Player p : Bukkit.getOnlinePlayers()) {
      response = response +
              "<tr><td><img src=\"https://minotar.net/avatar/%playername%/16\"></td><td>" +
              "<a href =\"/player_info?%playername%\">%playername% (UUID: %playeruuid%)</a> " +
              "<input type=\"button\" class=\"small\" value=\"Kick\" onclick=\"sendCommand('kick %playername%')\" />" +
              "<input type=\"button\" class=\"small\" value=\"Ban\" onclick=\"sendCommand('ban %playername% Du wurdest von einem Admin gebannt')\" /></p></td></tr>";
      response = response
              .replace("%playername%", p.getName())
              .replace("%playeruuid%", p.getUniqueId().toString());
    }
    if(response.trim().equalsIgnoreCase("<p>")) {
      response = "<p>Es sind keine Spieler online!</p>";
    }

    return response;
  }

  public static String getUsers(Main main) {
    File f = new File(main.getDataFolder() + "//users.yml");
    List<String> users = YamlConfiguration.loadConfiguration(f).getStringList("users");

    String str = "";
    for(String s : users) {
      if(s.split("---:::---")[0].equals("root")) {
        str = str + "<tr><td>" +
                s.split("---:::---")[0] +
                "</td><td><input type=\"button\" class=\"small\" value=\"Edit\" onclick=\"openEditor('%acc%')\" />" +
                "</td></tr>";
      } else {
        str = str + "<tr><td>" +
                s.split("---:::---")[0] +
                "</td><td><input type=\"button\" class=\"small\" value=\"Edit\" onclick=\"openEditor('%acc%')\" />" +
                "<input type=\"button\" class=\"small\" value=\"Delete\" onclick=\"sendCommand('delete_name:%acc%')\" />" +
                "</td></tr>";
      }

      str = str.replace("%acc%", s.split("---:::---")[0]);
    }

    return str;
  }

}
