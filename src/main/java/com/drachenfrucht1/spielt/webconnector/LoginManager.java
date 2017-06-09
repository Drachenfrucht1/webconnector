package com.drachenfrucht1.spielt.webconnector;

import com.sun.net.httpserver.BasicAuthenticator;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

/**
 * Created by Drachenfrucht1 on 10.05.2017.
 */
class LoginManager {

  private BasicAuthenticator basicAuthenticator;

  private File f = new File(Main.instance.getDataFolder() + "//users.yml");
  private YamlConfiguration cfg;

  LoginManager() {
    cfg = YamlConfiguration.loadConfiguration(f);

    basicAuthenticator = new BasicAuthenticator("/") {
      @Override
      public boolean checkCredentials(String username, String password) {
        return userExists(username, password);
      }
    };
  }

  private boolean userExists(String user, String password) {
    List<String> users = cfg.getStringList("users");

    for(String s : users) {
      if(s.split("---:::---")[0].equals(user) && s.split("---:::---")[1].equals(password)) {
        return true;
      }
    }

    return false;
  }

  private boolean userExists(String user) {
    List<String> users = cfg.getStringList("users");

    for(String s : users) {
      if(s.split("---:::---")[0].equals(user)) {
        return true;
      }
    }

    return false;
  }

  void addUser(String username, String password) {
    List<String> users = cfg.getStringList("users");

    if(!userExists(username)) {
      users.add(username + "---:::---" + password);
      cfg.set("users", users);
      saveConfig();
    }
  }

  void changeUser(String old_name, String name, String password) {
    List<String> users = cfg.getStringList("users");

    if(userExists(old_name)) {
      for(String s : users) {
        if(s.split("---:::---")[0].equals(old_name)) {
          users.remove(s);
          users.add(name + "---:::---" + password);
          cfg.set("users", users);
          saveConfig();
          break;
        }
      }
    } else {
      return;
    }
  }

  void deleteUser(String name) {
    List<String> users = cfg.getStringList("users");

    if(userExists(name)) {
      for(String s : users) {
        if(s.split("---:::---")[0].equals(name)) {
          users.remove(s);
          cfg.set("users", users);
          saveConfig();
          return;
        }
      }
    }
  }

  void saveConfig() {
    try {
      cfg.save(f);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public BasicAuthenticator getBasicAuthenticator() {
    return basicAuthenticator;
  }
}
