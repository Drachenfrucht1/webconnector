package com.drachenfrucht1.spielt.webconnector.handler;

import com.drachenfrucht1.spielt.webconnector.Main;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by Drachenfrucht1 on 11.05.2017.
 */
public class UserManager implements HttpHandler {

  private Main main;

  public UserManager(Main m) {
    main = m;
  }

  public void handle(HttpExchange httpExchange) throws IOException {
    String q = httpExchange.getRequestURI().getQuery().toString();

    String[] split = q.split("_");

    if (split[0].equals("save")) {
      main.getLoginManager().changeUser(split[1].split(":")[1], split[2].split(":")[1], split[3].split(":")[1]);
    } else if (split[0].equals("delete")) {
      main.getLoginManager().deleteUser(split[1].split(":")[1]);
    } else if (split[0].equals("add")) {
      main.getLoginManager().addUser(split[1].split(":")[1], split[2].split(":")[1]);
    }
  }
}
