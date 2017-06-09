package com.drachenfrucht1.spielt.webconnector;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Drachenfrucht1 on 09.05.2017.
 */
public class Main extends JavaPlugin {

  private WebServerManager webServerManager;
  private static LoginManager loginManager;

  static Main instance;

  @Override
  public void onEnable() {
    try {
      instance = this;

      webServerManager = new WebServerManager(40);
      loginManager = new LoginManager();
      new WebFileManager(this);
      webServerManager.getServer().setExecutor(null);
      webServerManager.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("[WebConnector] Plugin activated!");
  }

  @Override
  public void onDisable() {
    webServerManager.stop();
    loginManager.saveConfig();
    System.out.println("[WebConnector] Plugin deactivated!");
  }

  /*private void loadConfig() {
    getConfig().options().copyDefaults(true);
    saveConfig();
  }*/

  public WebServerManager getWebServerManager() {
    return webServerManager;
  }

  public static LoginManager getLoginManager() {
    return loginManager;
  }
}
