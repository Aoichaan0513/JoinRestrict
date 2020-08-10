package org.aoichaan0513.joinrestrict;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class JRListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();

        if (p.isBanned())
            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, Main.ErrorType.KICK_OTHER.getMessage());

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
                if (config.getInt(Main.ConfigType.MAXPLAYERS_SPONSOR.getName()) == -1 && config.getInt(Main.ConfigType.MAXPLAYERS_LISTENER.getName()) == -1) {
                    if (config.getBoolean(Main.ConfigType.ISENABLED_BLOCK.getName())) {
                        e.allow();
                    } else {
                        if (throughList.contains(p.getUniqueId().toString())) {
                            e.allow();
                        } else {
                            if (!blockList.contains(p.getUniqueId().toString())) {
                                e.allow();
                            } else {
                                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICK_BLOCKED.getMessage());
                            }
                        }
                    }
                } else {
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
                                        e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICK_BLOCKED.getMessage());
                                    }
                                }
                            } else {
                                if (Main.isAdmin(p)) {
                                    e.allow();
                                } else {
                                    if (throughList.contains(p.getUniqueId().toString())) {
                                        e.allow();
                                    } else {
                                        e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICK_FULLED_SPONSOR.getMessage());
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
                                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICK_SPONSOR.getMessage());
                                }
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
                                        e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICK_BLOCKED.getMessage());
                                    }
                                }
                            } else {
                                if (Main.isAdmin(p)) {
                                    e.allow();
                                } else {
                                    if (throughList.contains(p.getUniqueId().toString())) {
                                        e.allow();
                                    } else {
                                        e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICK_FULLED_LISTENER.getMessage());
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
                                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICK_SPONSOR.getMessage());
                                }
                            }
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
                    if (throughList.contains(p.getUniqueId().toString())) {
                        e.allow();
                    } else {
                        e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICK_FULLED_ALL.getMessage());
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
                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Main.ErrorType.KICK_CLOSED.getMessage());
                }
            }
        }
    }

    /*
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!Main.isAdmin(p)) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getConsoleSender().sendMessage(Main.getSecondaryPrefix() + "アップデートを確認しています…");
                p.sendMessage(Main.getSecondaryPrefix() + "アップデートを確認しています…");

                boolean result = Main.isUpdateAvailable();
                if (result) {
                    Bukkit.getConsoleSender().sendMessage(Main.getSecondaryPrefix() + "このバージョンは" + ChatColor.RED + "" + ChatColor.UNDERLINE + "最新版ではありません" + ChatColor.RESET + ChatColor.GRAY + "。\n" +
                            Main.getSecondaryPrefix() + "更新をする必要があります。開発者から最新版を受け取り、入れ替えて再起動をしてください。");
                    p.sendMessage(Main.getSecondaryPrefix() + "このバージョンは" + ChatColor.RED + "" + ChatColor.UNDERLINE + "最新版ではありません" + ChatColor.RESET + ChatColor.GRAY + "。\n" +
                            Main.getSecondaryPrefix() + "更新をする必要があります。開発者から最新版を受け取り、入れ替えて再起動をしてください。");
                } else  {
                    Bukkit.getConsoleSender().sendMessage(Main.getSecondaryPrefix() + "このバージョンは" + ChatColor.GREEN + "" + ChatColor.UNDERLINE + "最新版" + ChatColor.RESET + ChatColor.GRAY + "です。\n" +
                            Main.getSecondaryPrefix() + "更新の必要はありません。");
                    p.sendMessage(Main.getSecondaryPrefix() + "このバージョンは" + ChatColor.GREEN + "" + ChatColor.UNDERLINE + "最新版" + ChatColor.RESET + ChatColor.GRAY + "です。\n" +
                            Main.getSecondaryPrefix() + "更新の必要はありません。");
                }
            }
        }.runTaskLaterAsynchronously(Main.getInstance(), 100);
    }
    */

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        Main.sponsorList.remove(p.getUniqueId());
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
