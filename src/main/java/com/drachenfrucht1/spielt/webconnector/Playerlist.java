package com.drachenfrucht1.spielt.webconnector;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Drachenfrucht1 on 10.05.2017.
 */
public class Playerlist implements HttpHandler{

  static String getPlayerList() {
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

  public void handle(HttpExchange httpExchange) throws IOException {
    String playername = httpExchange.getRequestURI().getQuery();

    if(Bukkit.getPlayer(playername).isOnline()) {
      Player p =Bukkit.getPlayer(playername);
      String response = FileUtils.getFileContents("playerinfo.html", Main.instance);
      response = response
              .replace("%name%", p.getName())
              .replace("%uuid%", p.getUniqueId().toString())
              .replace("%gamemode%", p.getGameMode().name())
              .replace("%health%", String.valueOf(p.getHealth()))
              .replace("%food%", String.valueOf(p.getFoodLevel()))
              .replace("%world%", p.getLocation().getWorld().getName());

      httpExchange.sendResponseHeaders(200, response.length());

      OutputStream outputStream = httpExchange.getResponseBody();
      outputStream.write(response.getBytes());
      outputStream.close();
    }
  }
}
