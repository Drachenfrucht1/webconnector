package com.drachenfrucht1.spielt.webconnector;

import com.drachenfrucht1.spielt.webconnector.cmd.CmdUser;
import com.drachenfrucht1.spielt.webconnector.cmd.TcUser;
import com.drachenfrucht1.spielt.webconnector.listener.JoinListener;
import com.drachenfrucht1.spielt.webconnector.misc.LoginManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Drachenfrucht1 on 09.05.2017.
 */
public class Main extends JavaPlugin {

  //constants
  private final static @Getter String PRAEFIX = ChatColor.AQUA + "[" + ChatColor.GOLD + "Webinterface" + ChatColor.AQUA + "] " + ChatColor.GRAY;
  private @Getter WebServerManager webServerManager;
  private @Getter LoginManager loginManager;
  private @Getter @Setter int maxPlayers = 0;

  @Override
  public void onEnable() {
    try {
      loadConfig();

      webServerManager = new WebServerManager(getConfig().getInt("port"));
      loginManager = new LoginManager(this);
      new WebFileManager(this);
      webServerManager.getServer().setExecutor(null);
      webServerManager.start();

      registerEvents();
      registerCommands();
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

  private void registerCommands() {
    this.getCommand("user").setExecutor(new CmdUser(this));
    this.getCommand("user").setTabCompleter(new TcUser());
  }

  private void registerEvents() {
    Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
  }

  private void loadConfig() {
    getConfig().options().copyDefaults(true);
    saveConfig();
  }
}
