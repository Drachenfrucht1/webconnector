package com.drachenfrucht1.spielt.webconnector.cmd;

import com.drachenfrucht1.spielt.webconnector.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Dominik on 26.06.2017.
 * Version: 0.0.1
 * Project: webcon
 */
public class CmdWebconnector implements CommandExecutor {

  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

    if(cmd.getName().equalsIgnoreCase("webconnector")) {
      if(sender.hasPermission("web.webconnector"))
      if(args.length == 0) {
        sender.sendMessage("--------------------" + Main.getPRAEFIX() + "--------------------");
        sender.sendMessage("WebConnector is a plugin which adds a webinterface for your Minecraft Server!");
        sender.sendMessage("--------------------" + Main.getPRAEFIX() + "--------------------");
      }
    }
    return false;
  }
}
