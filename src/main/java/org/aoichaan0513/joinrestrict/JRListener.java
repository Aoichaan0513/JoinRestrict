package org.aoichaan0513.joinrestrict;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;

public class JRListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();

        FileConfiguration config = Main.getJRConfig(false);

        List<String> list = config.getStringList(Main.ConfigType.LIST_THROUGH.getName());

        System.out.println(Bukkit.getOnlinePlayers().size() - Bukkit.getOperators().size());
        System.out.println(Bukkit.getOnlinePlayers().stream().filter(player -> !Main.isAdmin(player)).count());

        int count = (int) Bukkit.getOnlinePlayers().stream().filter(player -> !Main.isAdmin(player)).count();
        int maxCount = config.getInt(Main.ConfigType.MAXPLAYERS_ALL.getName()) != -1 ? config.getInt(Main.ConfigType.MAXPLAYERS_ALL.getName()) : Bukkit.getMaxPlayers();

        if (config.getBoolean(Main.ConfigType.ISENABLED_MAIN.getName())) {
            if (count < maxCount) {
                e.allow();
            } else {
                if (Main.isAdmin(p) || list.contains(p.getUniqueId().toString())) {
                    e.allow();
                } else {
                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICKED_FULL.getMessage());
                }
            }
        } else {
            if (Main.isAdmin(p)) {
                e.allow();
            } else {
                if (list.contains(p.getUniqueId().toString())) {
                    e.allow();
                } else {
                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICKED_CLOSED.getMessage());
                }
            }
        }
    }
}
