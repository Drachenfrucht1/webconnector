package com.drachenfrucht1.spielt.webconnector.handler;

import com.drachenfrucht1.spielt.webconnector.Main;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * Created by Dominik on 14.06.2017.
 * Version: 0.0.1
 * Project: webcon
 */
public class PlayerChange implements HttpHandler {

  Main main;

  public PlayerChange(Main m) {
    main = m;
  }


  public void handle(HttpExchange httpExchange) throws IOException {
    String s = httpExchange.getRequestURI().getQuery();
    String[] split = s.split("_");
    Player p = null;

    for (int i = 0; i < split.length; i++) {
      if (split[i].contains("name")) {
        p = Bukkit.getPlayer(split[i].split("=")[1]);
      }
      if (split[i].contains("health")) {
        p.setHealth(Double.valueOf(split[i].split("=")[1]));
      }
      if (split[i].contains("food")) {
        p.setFoodLevel(Integer.parseInt(split[i].split("=")[1]));
      }
      if (split[i].contains("x")) {
        Location l = new Location(p.getLocation().getWorld(),
                Double.valueOf(split[i].split("=")[1]),
                p.getLocation().getY(),
                p.getLocation().getZ());
        p.teleport(l);
      }
      if (split[i].contains("y")) {
        Location l = new Location(p.getLocation().getWorld(),
                p.getLocation().getX(),
                Double.valueOf(split[i].split("=")[1]),
                p.getLocation().getZ());
        p.teleport(l);
      }
      if (split[i].contains("z")) {
        Location l = new Location(p.getLocation().getWorld(),
                p.getLocation().getX(),
                p.getLocation().getY(),
                Double.valueOf(split[i].split("=")[1]));
        p.teleport(l);
      }
      if (split[i].contains("world")) {
        Location l = new Location(Bukkit.getWorld(split[i].split("=")[1]),
                p.getLocation().getX(),
                p.getLocation().getY(),
                p.getLocation().getZ());
        p.teleport(l);
      }

    }
  }
}
