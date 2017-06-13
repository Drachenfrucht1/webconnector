package com.drachenfrucht1.spielt.webconnector.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 13.06.2017.
 * Version: 0.0.1
 * Project: webcon
 */
public class TcUser implements TabCompleter {

  public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

    if(cmd.getName().equalsIgnoreCase("user")) {
      if(sender.hasPermission("web.user.tc")) {
        if(args.length == 0) {
          List<String> tc = new ArrayList<String>();
          tc.add("add");
          tc.add("delete");
          tc.add("list");
        }
      }
    }
    return null;
  }
}
