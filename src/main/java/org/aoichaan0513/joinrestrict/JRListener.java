package org.aoichaan0513.joinrestrict;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class JRListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();

        Date date = new Date();

        JRCmd.blockMap.put(p.getUniqueId(), date.getTime());

        FileConfiguration config = Main.getJRConfig(false);

        List<String> throughList = config.getStringList(Main.ConfigType.LIST_THROUGH.getName());
        List<String> blockList = config.getStringList(Main.ConfigType.LIST_BLOCK.getName());

        System.out.println(Bukkit.getOnlinePlayers().size() - Bukkit.getOperators().size());
        System.out.println(Bukkit.getOnlinePlayers().stream().filter(player -> !Main.isAdmin(player)).count());

        int count = (int) Bukkit.getOnlinePlayers().stream().filter(player -> !Main.isAdmin(player)).count();
        int maxCount = config.getInt(Main.ConfigType.MAXPLAYERS_ALL.getName()) != -1 ? config.getInt(Main.ConfigType.MAXPLAYERS_ALL.getName()) : Bukkit.getMaxPlayers();

        int sponsorCount = getPlayerCount(Main.sponsorList);
        int listenerCount = getPlayerCount(Main.listenerList);

        if (config.getBoolean(Main.ConfigType.ISENABLED_MAIN.getName())) {
            if (count < maxCount) {
                if (Main.list.contains(p.getUniqueId())) {
                    /**
                     * スポンサー時の処理
                     */

                    if (config.getInt(Main.ConfigType.MAXPLAYERS_SPONSOR.getName()) != -1) {
                        if (sponsorCount < config.getInt(Main.ConfigType.MAXPLAYERS_SPONSOR.getName())) {
                            if (config.getBoolean(Main.ConfigType.ISENABLED_BLOCK.getName())) {
                                e.allow();

                                if (!Main.sponsorList.contains(p.getUniqueId()) && !Main.isAdmin(p))
                                    Main.sponsorList.add(p.getUniqueId());
                            } else {
                                if (!blockList.contains(p.getUniqueId().toString())) {
                                    e.allow();

                                    if (!Main.sponsorList.contains(p.getUniqueId()) && !Main.isAdmin(p))
                                        Main.sponsorList.add(p.getUniqueId());
                                } else {
                                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICKED_BLOCKED.getMessage());
                                }
                            }
                        } else {
                            if (Main.isAdmin(p)) {
                                e.allow();
                            } else {
                                if (config.getBoolean(Main.ConfigType.ISENABLED_BLOCK.getName()) ||
                                        (!config.getBoolean(Main.ConfigType.ISENABLED_BLOCK.getName()) && !blockList.contains(p.getUniqueId().toString()))) {
                                    if (throughList.contains(p.getUniqueId().toString())) {
                                        e.allow();

                                        if (!Main.sponsorList.contains(p.getUniqueId()) && !Main.isAdmin(p))
                                            Main.sponsorList.add(p.getUniqueId());
                                    } else {
                                        e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICKED_FULLED.getMessage());
                                    }
                                } else {
                                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICKED_BLOCKED.getMessage());
                                }
                            }
                        }
                    } else {
                        if (Main.isAdmin(p)) {
                            e.allow();
                        } else {
                            e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICKED_OTHER.getMessage());
                        }
                    }
                } else {
                    /**
                     * 通常時の処理
                     */

                    if (config.getInt(Main.ConfigType.MAXPLAYERS_LISTENER.getName()) != -1) {
                        if (listenerCount < config.getInt(Main.ConfigType.MAXPLAYERS_LISTENER.getName())) {
                            if (config.getBoolean(Main.ConfigType.ISENABLED_BLOCK.getName())) {
                                e.allow();

                                if (!Main.listenerList.contains(p.getUniqueId()) && !Main.isAdmin(p))
                                    Main.listenerList.add(p.getUniqueId());
                            } else {
                                if (!blockList.contains(p.getUniqueId().toString())) {
                                    e.allow();

                                    if (!Main.listenerList.contains(p.getUniqueId()) && !Main.isAdmin(p))
                                        Main.listenerList.add(p.getUniqueId());
                                } else {
                                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICKED_BLOCKED.getMessage());
                                }
                            }
                        } else {
                            if (Main.isAdmin(p)) {
                                e.allow();
                            } else {
                                if (config.getBoolean(Main.ConfigType.ISENABLED_BLOCK.getName()) ||
                                        (!config.getBoolean(Main.ConfigType.ISENABLED_BLOCK.getName()) && !blockList.contains(p.getUniqueId().toString()))) {
                                    if (throughList.contains(p.getUniqueId().toString())) {
                                        e.allow();

                                        if (!Main.sponsorList.contains(p.getUniqueId()) && !Main.isAdmin(p))
                                            Main.sponsorList.add(p.getUniqueId());
                                    } else {
                                        e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICKED_FULLED.getMessage());
                                    }
                                } else {
                                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICKED_BLOCKED.getMessage());
                                }
                            }
                        }
                    } else {
                        if (Main.isAdmin(p)) {
                            e.allow();
                        } else {
                            e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICKED_OTHER.getMessage());
                        }
                    }
                }
            } else {
                /**
                 * 全体的の上限に引っかかったときの処理
                 */
                if (Main.isAdmin(p)) {
                    e.allow();
                } else {
                    if (config.getBoolean(Main.ConfigType.ISENABLED_BLOCK.getName()) ||
                            (!config.getBoolean(Main.ConfigType.ISENABLED_BLOCK.getName()) && !blockList.contains(p.getUniqueId().toString()))) {
                        if (throughList.contains(p.getUniqueId().toString())) {
                            e.allow();
                        } else {
                            e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICKED_FULLED.getMessage());
                        }
                    } else {
                        e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICKED_BLOCKED.getMessage());
                    }
                }
            }
        } else {
            if (Main.isAdmin(p)) {
                e.allow();
            } else {
                if (throughList.contains(p.getUniqueId().toString())) {
                    e.allow();
                } else {
                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICKED_CLOSED.getMessage());
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (Main.sponsorList.contains(p.getUniqueId()))
            Main.sponsorList.remove(p.getUniqueId());
        if (Main.listenerList.contains(p.getUniqueId()))
            Main.listenerList.remove(p.getUniqueId());
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String cmd = e.getMessage();

        if (!p.isOp()) return;
        if (cmd.contains("start")) {
            if (Main.getJRConfig().getBoolean(Main.ConfigType.ISENABLED_MAIN.getName())) return;

            p.sendMessage(Main.getPrefix(ChatColor.YELLOW) + ChatColor.GOLD + "サーバーを開放していないようです。\n" +
                    Main.getPrefix(ChatColor.YELLOW) + ChatColor.GOLD + "サーバーを開放していない場合は\"" + ChatColor.UNDERLINE + "/jr open" + ChatColor.RESET + ChatColor.GOLD + "\"と入力してサーバーを開放してください。\n" +
                    Main.getSecondaryPrefix() + "また、すでにサーバーを開放している場合はこのメッセージを無視してください。");
        }
    }

    public static int getPlayerCount(List<UUID> list) {
        int i = 0;

        for (UUID uuid : list) {
            Player player = Bukkit.getPlayer(uuid);

            if (Main.isAdmin(player)) continue;
            i++;
        }

        return i;
    }
}
