package com.drachenfrucht1.spielt.webconnector;

import com.drachenfrucht1.spielt.webconnector.handler.Console;
import com.drachenfrucht1.spielt.webconnector.handler.Playerlist;
import com.drachenfrucht1.spielt.webconnector.handler.UserManager;
import com.drachenfrucht1.spielt.webconnector.misc.FileUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Drachenfrucht1 on 09.05.2017.
 */
public class WebFileManager implements HttpHandler{

  private Main main;
  private WebFileManager instance;

  public WebFileManager(Main m) {
    instance = this;
    main  = m;
    load();
  }

  public void load() {
    File dir = new File(main.getDataFolder() + "//webpages//");
    if(!dir.getParentFile().exists()) {
      dir.getParentFile().mkdirs();
    }

    if(!dir.exists()) {
      dir.mkdirs();
    }

    File[] files = dir.listFiles();

    if(files != null) {
      for(int i = 0;  i < files.length; i++) {
        if(!files[i].isDirectory()) {
          main.getWebServerManager().getServer().createContext("/" + files[i].getName(), instance)
                  .setAuthenticator(main.getLoginManager().getBasicAuthenticator());
        }
      }
    }
    main.getWebServerManager().getServer().createContext("/console_send", new Console(main))
            .setAuthenticator(main.getLoginManager().getBasicAuthenticator());
    main.getWebServerManager().getServer().createContext("/player_info", new Playerlist(main))
            .setAuthenticator(main.getLoginManager().getBasicAuthenticator());
    main.getWebServerManager().getServer().createContext("/", this)
            .setAuthenticator(main.getLoginManager().getBasicAuthenticator());
    main.getWebServerManager().getServer().createContext("/acc_cmd", new UserManager(main))
            .setAuthenticator(main.getLoginManager().getBasicAuthenticator());
  }

  public void handle(HttpExchange httpExchange) throws IOException {
    String response = FileUtils.getFileContents(httpExchange.getRequestURI().getPath(), main);

    response = response
            .replace("%console_view%", FileUtils.getLogContents())
            .replace("%player_list%", FileUtils.getPlayerList())
            .replace("%account_name%", httpExchange.getPrincipal().getUsername());

     response = response
             .replace("%account_list%", FileUtils.getUsers(main))
             .replace("%players_online%", String.valueOf(Bukkit.getOnlinePlayers().size()))
             .replace("%max_players%", String.valueOf(main.getMaxPlayers()));

    httpExchange.sendResponseHeaders(200, response.length());

    OutputStream outputStream = httpExchange.getResponseBody();
    outputStream.write(response.getBytes());
    outputStream.close();
  }
}
