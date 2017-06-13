package com.drachenfrucht1.spielt.webconnector.cmd;

import com.drachenfrucht1.spielt.webconnector.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 11.06.2017.
 * Version: 0.0.1
 * Project: webcon
 */
public class CmdUser implements CommandExecutor {

  private Main main;

  public CmdUser(Main m) {
    main = m;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

    if(cmd.getName().equalsIgnoreCase("user")) {

      if(args[0].equalsIgnoreCase("add")) {
        if(sender.hasPermission("web.user.add")) {
          if (args.length != 4) return false;

          if(!main.getLoginManager().userExists(args[1])) {
            if(args[2].equals(args[3])) {
              main.getLoginManager().addUser(args[1], args[2]);
              sender.sendMessage(Main.getPRAEFIX() + "You added the user " + args[1] + " to the webinterface!");
              return true;
            } else {
              sender.sendMessage(Main.getPRAEFIX() + ChatColor.DARK_RED + "The passwords have to be the same!");
              return true;
            }
          } else {
            sender.sendMessage(Main.getPRAEFIX() + ChatColor.DARK_RED + "This user already exists!");
            return true;
          }
        } else {
          sender.sendMessage(Main.getPRAEFIX() + ChatColor.DARK_RED + "You don't have enough permissions!");
          return true;
        }

      } else if(args[0].equalsIgnoreCase("delete")) {
        if(sender.hasPermission("web.user.delete")) {
          if (args.length != 2) return false;

          if(main.getLoginManager().userExists(args[1])) {
            main.getLoginManager().deleteUser(args[1]);
            sender.sendMessage(Main.getPRAEFIX() + "You removed user " + args[1] + " from the webinterface!");
            return true;
          } else {
            sender.sendMessage(Main.getPRAEFIX() + ChatColor.DARK_RED + "This user doesn't exists!");
            return true;
          }
        } else {
          sender.sendMessage(Main.getPRAEFIX() + ChatColor.DARK_RED + "You don't have enough permissions!");
          return true;
        }

      } else if(args[0].equalsIgnoreCase("list")) {
        if(sender.hasPermission("web.user.list")) {
          if (args.length != 1) return false;

          String out = Main.getPRAEFIX() + "Webinterface users: ";
          List<String> users = main.getLoginManager().getCfg().getStringList("users");
          for(int i = 0; i<users.size(); i++) {
            out = out + users.get(i).split("---:::---")[0];
          }
          sender.sendMessage(out);
          return true;
        } else {
          sender.sendMessage(Main.getPRAEFIX() + ChatColor.DARK_RED + "You don't have enough permissions!");
          return true;
        }

      }
    }
    return false;
  }
}
