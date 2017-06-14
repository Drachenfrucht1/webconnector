package com.drachenfrucht1.spielt.webconnector.listener;

import com.drachenfrucht1.spielt.webconnector.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Dominik on 09.06.2017.
 * Version: 0.0.1
 * Project: webcon
 * <p>
 * brief description
 */
public class JoinListener implements Listener {

  Main main;

  public JoinListener(Main m) {
    main = m;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    if (main.getMaxPlayers() < Bukkit.getOnlinePlayers().size()) {
      main.setMaxPlayers(Bukkit.getOnlinePlayers().size());
    }
  }
}
