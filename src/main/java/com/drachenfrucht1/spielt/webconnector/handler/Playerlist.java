package com.drachenfrucht1.spielt.webconnector.handler;

import com.drachenfrucht1.spielt.webconnector.Main;
import com.drachenfrucht1.spielt.webconnector.misc.FileUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Drachenfrucht1 on 10.05.2017.
 */
public class Playerlist implements HttpHandler {

  Main main;

  public Playerlist(Main m) {
    main = m;
  }

  public void handle(HttpExchange httpExchange) throws IOException {
    String playername = httpExchange.getRequestURI().getQuery();

    if (Bukkit.getPlayer(playername).isOnline()) {
      Player p = Bukkit.getPlayer(playername);
      String response = FileUtils.getFileContents("playerinfo.html", main);
      response = response
              .replace("%name%", p.getName())
              .replace("%uuid%", p.getUniqueId().toString())
              .replace("%gamemode%", p.getGameMode().name())
              .replace("%health%", String.valueOf((int)p.getHealth()))
              .replace("%food%", String.valueOf(p.getFoodLevel()))
              .replace("%x%", String.valueOf((int)p.getLocation().getX()))
              .replace("%y%", String.valueOf((int)p.getLocation().getY()))
              .replace("%z%", String.valueOf((int)p.getLocation().getZ()))
              .replace("%world%", p.getLocation().getWorld().getName())
              .replace("%account_name%", httpExchange.getPrincipal().getUsername());

      httpExchange.sendResponseHeaders(200, response.length());

      OutputStream outputStream = httpExchange.getResponseBody();
      outputStream.write(response.getBytes());
      outputStream.close();
    }
  }
}
