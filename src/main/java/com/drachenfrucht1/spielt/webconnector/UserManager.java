package com.drachenfrucht1.spielt.webconnector;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Drachenfrucht1 on 11.05.2017.
 */
public class UserManager implements HttpHandler{

  static File f = new File(Main.instance.getDataFolder() + "//users.yml");

  private static List<String> getUserList() {
    try {
      return YamlConfiguration.loadConfiguration(f).getStringList("users");
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  static String users() {
    String str = "";
    for(String s : getUserList()) {
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

  public void handle(HttpExchange httpExchange) throws IOException {
    String q = httpExchange.getRequestURI().getQuery().toString();

    String[] split = q.split("_");

    if(split[0].equals("save")) {
      Main.getLoginManager().changeUser(split[1].split(":")[1], split[2].split(":")[1], split[3].split(":")[1]);
    } else if(split[0].equals("delete")) {
      Main.getLoginManager().deleteUser(split[1].split(":")[1]);
    } else if(split[0].equals("add")) {
      Main.getLoginManager().addUser(split[1].split(":")[1], split[2].split(":")[1]);
    }
  }
}
