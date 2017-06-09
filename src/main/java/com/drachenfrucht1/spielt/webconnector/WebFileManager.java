package com.drachenfrucht1.spielt.webconnector;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

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
                  .setAuthenticator(Main.getLoginManager().getBasicAuthenticator());
        }
      }
    }
    main.getWebServerManager().getServer().createContext("/console_send", new Console())
            .setAuthenticator(Main.getLoginManager().getBasicAuthenticator());
    main.getWebServerManager().getServer().createContext("/player_info", new Playerlist())
            .setAuthenticator(Main.getLoginManager().getBasicAuthenticator());
    main.getWebServerManager().getServer().createContext("/", this)
            .setAuthenticator(Main.getLoginManager().getBasicAuthenticator());
    main.getWebServerManager().getServer().createContext("/acc_cmd", new UserManager())
            .setAuthenticator(Main.getLoginManager().getBasicAuthenticator());
  }

  public void handle(HttpExchange httpExchange) throws IOException {
    String response = FileUtils.getFileContents(httpExchange.getRequestURI().getPath(), main);

    response = response
            .replace("%console_view%", Console.getLog())
            .replace("%player_list%", Playerlist.getPlayerList())
            .replace("%account_name%", httpExchange.getPrincipal().getUsername());

     response = response.replace("%account_list%", UserManager.users());

    httpExchange.sendResponseHeaders(200, response.length());

    OutputStream outputStream = httpExchange.getResponseBody();
    outputStream.write(response.getBytes());
    outputStream.close();
  }
}
