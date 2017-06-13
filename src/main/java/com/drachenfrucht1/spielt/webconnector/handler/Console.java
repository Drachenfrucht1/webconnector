package com.drachenfrucht1.spielt.webconnector.handler;

import com.drachenfrucht1.spielt.webconnector.Main;
import com.drachenfrucht1.spielt.webconnector.misc.FileUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.Bukkit;

import java.io.IOException;

/**
 * Created by Drachenfrucht1 on 10.05.2017.
 */
public class Console implements HttpHandler{

  private Main main;

  public Console(Main m) {
    main = m;
  }

  private static void sendCommand(final String command, Main main) {
    Bukkit.getScheduler().runTask(main, new Runnable() {
      public void run() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
      }
    });
  }

  public void handle(HttpExchange httpExchange) throws IOException {
    sendCommand(httpExchange.getRequestURI().getQuery().toString(), main);
  }
}
