package com.drachenfrucht1.spielt.webconnector;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;

import java.net.InetSocketAddress;

/**
 * Created by Dominik on 09.05.2017.
 */
public class WebServerManager {

  private HttpServer server;

  public WebServerManager(int port) {
    try {
      server = HttpServer.create(new InetSocketAddress(port), 0);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void start() {
    server.start();
  }

  public void stop() {
    server.stop(0);
  }

  public HttpServer getServer() {
    return server;
  }
}
