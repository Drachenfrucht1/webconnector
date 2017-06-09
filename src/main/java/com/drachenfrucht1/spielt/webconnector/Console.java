package com.drachenfrucht1.spielt.webconnector;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.Bukkit;

import java.io.IOException;

/**
 * Created by Drachenfrucht1 on 10.05.2017.
 */
public class Console implements HttpHandler{

  static String getLog() {
    return FileUtils.getLogContents();
  }

  private static void sendCommand(final String command, Main main) {
    Bukkit.getScheduler().runTask(main, new Runnable() {
      public void run() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
      }
    });
  }

  public void handle(HttpExchange httpExchange) throws IOException {
    sendCommand(httpExchange.getRequestURI().getQuery().toString(), Main.instance);
  }
}
