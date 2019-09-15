package org.aoichaan0513.joinrestrict;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONArray;

import javax.xml.soap.Text;
import java.util.*;

public class JRCmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (Main.isAdmin(sender)) {
                if (args.length != 0) {
                    if (args[0].equalsIgnoreCase("status") || args[0].equalsIgnoreCase("info")) {
                        runStatus(sender, cmd, label, args);
                        return true;
                    } else if (args[0].equalsIgnoreCase("reload")) {
                        runReload(sender, cmd, label, args);
                        return true;
                    } else if (args[0].equalsIgnoreCase("open")) {
                        runOpen(sender, cmd, label, args);
                        return true;
                    } else if (args[0].equalsIgnoreCase("close")) {
                        runClose(sender, cmd, label, args);
                        return true;
                    } else if (args[0].equalsIgnoreCase("count")) {
                        runCount(sender, cmd, label, args);
                        return true;
                    } else if (args[0].equalsIgnoreCase("add")) {
                        runAdd(sender, cmd, label, args);
                        return true;
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        runRemove(sender, cmd, label, args);
                        return true;
                    } else if (args[0].equalsIgnoreCase("allow")) {
                        runAllow(sender, cmd, label, args);
                        return true;
                    } else if (args[0].equalsIgnoreCase("deny")) {
                        runDeny(sender, cmd, label, args);
                        return true;
                    } else if (args[0].equalsIgnoreCase("save")) {
                        runSave(sender, cmd, label, args);
                        return true;
                    } else if (args[0].equalsIgnoreCase("reset")) {
                        runReset(sender, cmd, label, args);
                        return true;
                    }
                }
                sendHelpMessage(sender, label);
                return true;
            }
            sender.sendMessage(Main.getErrorPrefix() + Main.ErrorType.PERMISSION.getMessage());
            return true;
        } else {
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("status") || args[0].equalsIgnoreCase("info")) {
                    runStatus(sender, cmd, label, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    runReload(sender, cmd, label, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("open")) {
                    runOpen(sender, cmd, label, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("close")) {
                    runClose(sender, cmd, label, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("count")) {
                    runCount(sender, cmd, label, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("add")) {
                    runAdd(sender, cmd, label, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("remove")) {
                    runRemove(sender, cmd, label, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("allow")) {
                    runAllow(sender, cmd, label, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("deny")) {
                    runDeny(sender, cmd, label, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("save")) {
                    runSave(sender, cmd, label, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("reset")) {
                    runReset(sender, cmd, label, args);
                    return true;
                }
            }
            sendHelpMessage(sender, label);
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("jr")) return null;

        if (sender instanceof Player) {
            if (Main.isAdmin(sender)) {
                if (args.length == 1) {
                    if (args[0].length() == 0) {
                        return Arrays.asList("status", "info", "reload", "open", "close", "count", "add", "remove", "allow", "deny", "save", "reset");
                    } else {
                        if ("status".startsWith(args[0])) {
                            return Collections.singletonList("status");
                        } else if ("info".startsWith(args[0])) {
                            return Collections.singletonList("info");
                        } else if ("reload".startsWith(args[0])) {
                            return Collections.singletonList("reload");
                        } else if ("open".startsWith(args[0])) {
                            return Collections.singletonList("open");
                        } else if ("close".startsWith(args[0])) {
                            return Collections.singletonList("close");
                        } else if ("count".startsWith(args[0])) {
                            return Collections.singletonList("count");
                        } else if ("add".startsWith(args[0])) {
                            return Collections.singletonList("add");
                        } else if ("remove".startsWith(args[0])) {
                            return Collections.singletonList("remove");
                        } else if ("allow".startsWith(args[0])) {
                            return Collections.singletonList("allow");
                        } else if ("deny".startsWith(args[0])) {
                            return Collections.singletonList("deny");
                        } else if ("save".startsWith(args[0])) {
                            return Collections.singletonList("save");
                        } else if ("reset".startsWith(args[0])) {
                            return Collections.singletonList("reset");
                        }
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("count")) {
                        if (args[1].length() == 0) {
                            return Arrays.asList("all", "sponsor", "listener");
                        } else {
                            if ("all".startsWith(args[0])) {
                                return Collections.singletonList("all");
                            } else if ("sponsor".startsWith(args[0])) {
                                return Collections.singletonList("sponsor");
                            } else if ("listener".startsWith(args[0])) {
                                return Collections.singletonList("listener");
                            }
                        }
                    }
                }
            }
        } else {
            if (args.length == 1) {
                if (args[0].length() == 0) {
                    return Arrays.asList("status", "info", "open", "close", "count", "add", "remove", "allow", "deny", "save", "reset");
                } else {
                    if ("status".startsWith(args[0])) {
                        return Collections.singletonList("status");
                    } else if ("info".startsWith(args[0])) {
                        return Collections.singletonList("info");
                    } else if ("reload".startsWith(args[0])) {
                        return Collections.singletonList("reload");
                    } else if ("open".startsWith(args[0])) {
                        return Collections.singletonList("open");
                    } else if ("close".startsWith(args[0])) {
                        return Collections.singletonList("close");
                    } else if ("count".startsWith(args[0])) {
                        return Collections.singletonList("count");
                    } else if ("add".startsWith(args[0])) {
                        return Collections.singletonList("add");
                    } else if ("remove".startsWith(args[0])) {
                        return Collections.singletonList("remove");
                    } else if ("allow".startsWith(args[0])) {
                        return Collections.singletonList("allow");
                    } else if ("deny".startsWith(args[0])) {
                        return Collections.singletonList("deny");
                    } else if ("save".startsWith(args[0])) {
                        return Collections.singletonList("save");
                    } else if ("reset".startsWith(args[0])) {
                        return Collections.singletonList("reset");
                    }
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("count")) {
                    if (args[1].length() == 0) {
                        return Arrays.asList("all", "sponsor", "listener");
                    } else {
                        if ("all".startsWith(args[0])) {
                            return Collections.singletonList("all");
                        } else if ("sponsor".startsWith(args[0])) {
                            return Collections.singletonList("sponsor");
                        } else if ("listener".startsWith(args[0])) {
                            return Collections.singletonList("listener");
                        }
                    }
                }
            }
        }
        return null;
    }

    public static HashMap<UUID, Long> blockMap = new HashMap<>();

    private void sendHelpMessage(CommandSender sender, String label) {
        sender.sendMessage(Main.getErrorPrefix() + Main.ErrorType.ARGS.getMessage() + "\n" +
                Main.getErrorPrefix() + "使い方:\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " status (info)" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "プラグイン情報を表示\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " reload" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "データを再取得\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " open" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "サーバーを開放\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " close" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "サーバーを閉鎖\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " count ..." + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "参加上限を変更\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " add <Name>" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "プレイヤーをスルーリストに追加\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " remove <Name>" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "プレイヤーをスルーリストから削除\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " allow" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "連戦を許可\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " deny" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "連戦を禁止\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " save" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "連戦リストに追加\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " reset" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "連戦リストをリセット");
        return;
    }

    private void runStatus(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration config = Main.getJRConfig();

        if (args.length != 1) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);

            sender.sendMessage(Main.getSecondaryPrefix() + player.getName() + " の情報\n" +
                    Main.getWarningPrefix() + "スルーリスト" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (config.getStringList(Main.ConfigType.LIST_THROUGH.getName()).contains(player.getUniqueId().toString()) ? "はい" : "いいえ") + "\n" +
                    Main.getWarningPrefix() + "連戦リスト" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (config.getStringList(Main.ConfigType.LIST_BLOCK.getName()).contains(player.getUniqueId().toString()) ? "はい" : "いいえ"));
            return;
        }

        int sponsorCount = JRListener.getPlayerCount(Main.sponsorList);
        int listenerCount = JRListener.getPlayerCount(Main.listenerList);

        if (sender instanceof Player) {
            TextComponent textComponent1 = new TextComponent(Main.getSecondaryPrefix() + "サーバー情報\n" +
                    Main.getWarningPrefix() + "サーバー開放" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (config.getBoolean(Main.ConfigType.ISENABLED_MAIN.getName()) ? "はい" : "いいえ") + "\n" +
                    Main.getWarningPrefix() + "連戦許可" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (config.getBoolean(Main.ConfigType.ISENABLED_BLOCK.getName()) ? "はい" : "いいえ") + "\n" +
                    Main.getWarningPrefix() + "全体の参加上限" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (config.getInt(Main.ConfigType.MAXPLAYERS_ALL.getName()) != -1 ? config.getInt(Main.ConfigType.MAXPLAYERS_ALL.getName()) : Bukkit.getMaxPlayers()) + "人");

            TextComponent textComponent2 = new TextComponent(ChatColor.GRAY + " (権限なし: " + Bukkit.getOnlinePlayers().stream().filter(player -> !Main.isAdmin(player)).count() + "人、権限あり: " + Bukkit.getOnlinePlayers().stream().filter(player -> Main.isAdmin(player)).count() + "人)\n");

            StringBuffer stringBuffer1 = new StringBuffer();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!Main.isAdmin(player)) continue;
                stringBuffer1.append(ChatColor.YELLOW + player.getName() + ChatColor.GRAY + ", ");
            }
            String str1 = stringBuffer1.toString();

            textComponent2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(str1.length() > 2 ? str1.substring(0, str1.length() - 2) : ChatColor.YELLOW + "なし").create()));

            TextComponent textComponent3 = new TextComponent(Main.getWarningPrefix() + "スポンサーの参加上限" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (config.getInt(Main.ConfigType.MAXPLAYERS_SPONSOR.getName()) != -1 ? config.getInt(Main.ConfigType.MAXPLAYERS_SPONSOR.getName()) + "人" : "無効"));
            TextComponent textComponent4 = new TextComponent((config.getInt(Main.ConfigType.MAXPLAYERS_SPONSOR.getName()) != -1 ? ChatColor.GRAY + " (" + sponsorCount + "人)" : "") + "\n");

            StringBuffer stringBuffer2 = new StringBuffer();
            for (UUID uuid : Main.sponsorList) {
                Player player = Bukkit.getPlayer(uuid);
                stringBuffer2.append(ChatColor.YELLOW + player.getName() + ChatColor.GRAY + ", ");
            }
            String str2 = stringBuffer2.toString();

            textComponent4.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(config.getInt(Main.ConfigType.MAXPLAYERS_SPONSOR.getName()) != -1 ? (str2.length() > 2 ? str2.substring(0, str2.length() - 2) : ChatColor.YELLOW + "なし") : ChatColor.YELLOW + "無効").create()));

            textComponent3.addExtra(textComponent4);

            TextComponent textComponent5 = new TextComponent(Main.getWarningPrefix() + "リスナーの参加上限" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (config.getInt(Main.ConfigType.MAXPLAYERS_LISTENER.getName()) != -1 ? config.getInt(Main.ConfigType.MAXPLAYERS_LISTENER.getName()) + "人" : "無効"));
            TextComponent textComponent6 = new TextComponent((config.getInt(Main.ConfigType.MAXPLAYERS_LISTENER.getName()) != -1 ? ChatColor.GRAY + " (" + listenerCount + "人)" : "") + "\n");

            StringBuffer stringBuffer3 = new StringBuffer();
            for (UUID uuid : Main.listenerList) {
                Player player = Bukkit.getPlayer(uuid);
                stringBuffer3.append(ChatColor.YELLOW + player.getName() + ChatColor.GRAY + ", ");
            }
            String str3 = stringBuffer3.toString();

            textComponent6.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(config.getInt(Main.ConfigType.MAXPLAYERS_LISTENER.getName()) != -1 ? (str3.length() > 2 ? str3.substring(0, str3.length() - 2) : ChatColor.YELLOW + "なし") : ChatColor.YELLOW + "無効").create()));

            textComponent5.addExtra(textComponent6);

            TextComponent textComponent7 = new TextComponent(Main.getWarningPrefix() + "スルーリスト" + ChatColor.GRAY + ": " + ChatColor.YELLOW + toString(config.getStringList(Main.ConfigType.LIST_THROUGH.getName()), true) + "\n" +
                    Main.getWarningPrefix() + "連戦リスト" + ChatColor.GRAY + ": " + ChatColor.YELLOW + toString(config.getStringList(Main.ConfigType.LIST_BLOCK.getName()), true));

            textComponent1.addExtra(textComponent2);
            textComponent1.addExtra(textComponent3);
            textComponent1.addExtra(textComponent5);
            textComponent1.addExtra(textComponent7);

            ((Player) sender).spigot().sendMessage(textComponent1);
            return;
        } else {
            sender.sendMessage(Main.getSecondaryPrefix() + "サーバー情報\n" +
                    Main.getWarningPrefix() + "サーバー開放" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (config.getBoolean(Main.ConfigType.ISENABLED_MAIN.getName()) ? "はい" : "いいえ") + "\n" +
                    Main.getWarningPrefix() + "連戦許可" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (config.getBoolean(Main.ConfigType.ISENABLED_BLOCK.getName()) ? "はい" : "いいえ") + "\n" +
                    Main.getWarningPrefix() + "全体の参加上限" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (config.getInt(Main.ConfigType.MAXPLAYERS_ALL.getName()) != -1 ? config.getInt(Main.ConfigType.MAXPLAYERS_ALL.getName()) : Bukkit.getMaxPlayers()) + "人" + ChatColor.GRAY + " (権限なし: " + Bukkit.getOnlinePlayers().stream().filter(player -> !Main.isAdmin(player)).count() + "人、権限あり: " + Bukkit.getOnlinePlayers().stream().filter(player -> Main.isAdmin(player)).count() + "人)\n" +
                    Main.getWarningPrefix() + "スポンサーの参加上限" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (config.getInt(Main.ConfigType.MAXPLAYERS_SPONSOR.getName()) != -1 ? config.getInt(Main.ConfigType.MAXPLAYERS_SPONSOR.getName()) + "人" : "無効") + (config.getInt(Main.ConfigType.MAXPLAYERS_SPONSOR.getName()) != -1 ? ChatColor.GRAY + " (" + sponsorCount + "人)" : "") + "\n" +
                    Main.getWarningPrefix() + "リスナーの参加上限" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (config.getInt(Main.ConfigType.MAXPLAYERS_LISTENER.getName()) != -1 ? config.getInt(Main.ConfigType.MAXPLAYERS_LISTENER.getName()) + "人" : "無効") + (config.getInt(Main.ConfigType.MAXPLAYERS_LISTENER.getName()) != -1 ? ChatColor.GRAY + " (" + listenerCount + "人)" : "") + "\n" +
                    Main.getWarningPrefix() + "スルーリスト" + ChatColor.GRAY + ": " + ChatColor.YELLOW + toString(config.getStringList(Main.ConfigType.LIST_THROUGH.getName()), true) + "\n" +
                    Main.getWarningPrefix() + "連戦リスト" + ChatColor.GRAY + ": " + ChatColor.YELLOW + toString(config.getStringList(Main.ConfigType.LIST_BLOCK.getName()), true));
            return;
        }

    }

    private void runReload(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage(Main.getSecondaryPrefix() + "スポンサーデータを更新しています…");
        new BukkitRunnable() {
            @Override
            public void run() {
                List<UUID> list = Main.list;
                Main.list.clear();

                String result = Main.sendPost("https://yschecker.aoichaan0513.xyz/api", "{\"type\":\"getSponsors\"}");
                JSONArray jsonArray = new JSONArray(result.substring(0, result.length() - 2) + "]");
                for (Object object : jsonArray.toList()) {
                    Main.list.add(UUID.fromString(String.valueOf(object)));
                }
                sender.sendMessage(Main.getSecondaryPrefix() + "スポンサーデータを更新しました。\n" +
                        Main.getWarningPrefix() + "スポンサーの人数" + ChatColor.GRAY + ": " + ChatColor.YELLOW + Main.list.size() + "人" + ChatColor.GRAY + " (" + getDifferenceString(list.size(), Main.list.size()) + ")");
            }
        }.runTaskLaterAsynchronously(Main.getInstance(), 0);
        return;
    }

    private void runOpen(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration config = Main.getJRConfig();

        final Main.ConfigType configType = Main.ConfigType.ISENABLED_MAIN;

        if (!config.getBoolean(configType.getName())) {
            config.set(configType.getName(), true);
            Main.saveJRConfig();

            sender.sendMessage(Main.getSecondaryPrefix() + "サーバーを" + ChatColor.GREEN + ChatColor.UNDERLINE + "開放" + ChatColor.RESET + ChatColor.GRAY + "しました。");
            return;
        }
        sender.sendMessage(Main.getErrorPrefix() + "サーバーはすでに開放されています。\n" +
                Main.getSecondaryPrefix() + "サーバーを閉鎖するには\"" + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " close" + ChatColor.RESET + ChatColor.GRAY + "\"と実行してください。");
        return;
    }

    private void runClose(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration config = Main.getJRConfig();

        final Main.ConfigType configType = Main.ConfigType.ISENABLED_MAIN;

        if (config.getBoolean(configType.getName())) {
            config.set(configType.getName(), false);
            Main.saveJRConfig();

            for (Player player : Bukkit.getOnlinePlayers())
                if (!Main.isAdmin(player))
                    player.kickPlayer(Main.ErrorType.CLOSED.getMessage());

            sender.sendMessage(Main.getSecondaryPrefix() + "サーバーを" + ChatColor.RED + ChatColor.UNDERLINE + "閉鎖" + ChatColor.RESET + ChatColor.GRAY + "しました。");
            return;
        }
        sender.sendMessage(Main.getErrorPrefix() + "サーバーはすでに閉鎖されています。\n" +
                Main.getSecondaryPrefix() + "サーバーを開放するには\"" + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " open" + ChatColor.RESET + ChatColor.GRAY + "\"と実行してください。");
        return;
    }

    private void runCount(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            if (args[1].equalsIgnoreCase("all")) {
                final String typeName = "全体";
                final Main.ConfigType configType = Main.ConfigType.MAXPLAYERS_ALL;

                if (args.length != 2) {
                    if (args[2].equalsIgnoreCase("none")) {
                        FileConfiguration config = Main.getJRConfig();

                        config.set(configType.getName(), -1);
                        Main.saveJRConfig();
                        sender.sendMessage(Main.getSuccessPrefix() + typeName + "が参加できる人数を" + ChatColor.GOLD + ChatColor.UNDERLINE + "最大人数" + ChatColor.RESET + ChatColor.GREEN + "までに設定しました。");
                        return;
                    } else {
                        try {
                            FileConfiguration config = Main.getJRConfig();

                            int count = Integer.parseInt(args[2]);

                            config.set(configType.getName(), count);
                            Main.saveJRConfig();
                            sender.sendMessage(Main.getSuccessPrefix() + typeName + "が参加できる人数を" + ChatColor.GOLD + ChatColor.UNDERLINE + count + "人" + ChatColor.RESET + ChatColor.GREEN + "までに設定しました。");
                            return;
                        } catch (NumberFormatException err) {
                            sender.sendMessage(Main.getErrorPrefix() + Main.ErrorType.NUMBER.getMessage());
                            return;
                        }
                    }
                } else {
                    FileConfiguration config = Main.getJRConfig();

                    if (config.getInt(configType.getName()) != -1) {
                        sender.sendMessage(Main.getSecondaryPrefix() + "現在の設定値は" + ChatColor.GOLD + ChatColor.UNDERLINE + config.getInt(configType.getName()) + "人" + ChatColor.RESET + ChatColor.GRAY + "に設定されています。");
                        return;
                    } else {
                        sender.sendMessage(Main.getSecondaryPrefix() + "現在の設定値は" + ChatColor.GOLD + ChatColor.UNDERLINE + Bukkit.getMaxPlayers() + "人" + ChatColor.RESET + ChatColor.GRAY + "に設定されています。");
                        return;
                    }
                }
            } else if (args[1].equalsIgnoreCase("sponsor") || args[1].equalsIgnoreCase("s")) {
                final String typeName = "スポンサー";
                final Main.ConfigType configType = Main.ConfigType.MAXPLAYERS_SPONSOR;

                if (args.length != 2) {
                    if (args[2].equalsIgnoreCase("none")) {
                        FileConfiguration config = Main.getJRConfig();

                        config.set(configType.getName(), -1);
                        Main.saveJRConfig();
                        sender.sendMessage(Main.getSuccessPrefix() + "この機能を無効にしました。\n" +
                                Main.getSecondaryPrefix() + "機能を再度有効にするには引数に数値を指定してください。");
                        return;
                    } else {
                        try {
                            FileConfiguration config = Main.getJRConfig();

                            int count = Integer.parseInt(args[2]);

                            if (config.getInt(Main.ConfigType.MAXPLAYERS_ALL.getName()) >= count) {
                                config.set(configType.getName(), count);
                                Main.saveJRConfig();
                                sender.sendMessage(Main.getSuccessPrefix() + typeName + "が参加できる人数を" + ChatColor.GOLD + ChatColor.UNDERLINE + count + "人" + ChatColor.RESET + ChatColor.GREEN + "までに設定しました。");
                                return;
                            }
                            sender.sendMessage(Main.getErrorPrefix() + "全体が参加できる人数以上を指定することは出来ません。");
                            return;
                        } catch (NumberFormatException err) {
                            sender.sendMessage(Main.getErrorPrefix() + Main.ErrorType.NUMBER.getMessage());
                            return;
                        }
                    }
                } else {
                    FileConfiguration config = Main.getJRConfig();

                    if (config.getInt(configType.getName()) != -1) {
                        sender.sendMessage(Main.getSecondaryPrefix() + "現在の設定値は" + ChatColor.GOLD + ChatColor.UNDERLINE + config.getInt(configType.getName()) + "人" + ChatColor.RESET + ChatColor.GRAY + "に設定されています。");
                        return;
                    } else {
                        sender.sendMessage(Main.getSecondaryPrefix() + "この機能は" + ChatColor.RED + ChatColor.UNDERLINE + "無効" + ChatColor.RESET + ChatColor.GRAY + "になっています。");
                        return;
                    }
                }
            } else if (args[1].equalsIgnoreCase("listener") || args[1].equalsIgnoreCase("l")) {
                final String typeName = "リスナー";
                final Main.ConfigType configType = Main.ConfigType.MAXPLAYERS_LISTENER;

                if (args.length != 2) {
                    if (args[2].equalsIgnoreCase("none")) {
                        FileConfiguration config = Main.getJRConfig();

                        config.set(configType.getName(), -1);
                        Main.saveJRConfig();
                        sender.sendMessage(Main.getSuccessPrefix() + "この機能を無効にしました。\n" +
                                Main.getSecondaryPrefix() + "機能を再度有効にするには引数に数値を指定してください。");
                        return;
                    } else {
                        try {
                            FileConfiguration config = Main.getJRConfig();

                            int count = Integer.parseInt(args[2]);

                            if (config.getInt(Main.ConfigType.MAXPLAYERS_ALL.getName()) >= count) {
                                config.set(configType.getName(), count);
                                Main.saveJRConfig();
                                sender.sendMessage(Main.getSuccessPrefix() + typeName + "が参加できる人数を" + ChatColor.GOLD + ChatColor.UNDERLINE + count + "人" + ChatColor.RESET + ChatColor.GREEN + "までに設定しました。");
                                return;
                            }
                            sender.sendMessage(Main.getErrorPrefix() + "全体が参加できる人数以上を指定することは出来ません。");
                            return;
                        } catch (NumberFormatException err) {
                            sender.sendMessage(Main.getErrorPrefix() + Main.ErrorType.NUMBER.getMessage());
                            return;
                        }
                    }
                } else {
                    FileConfiguration config = Main.getJRConfig();

                    if (config.getInt(configType.getName()) != -1) {
                        sender.sendMessage(Main.getSecondaryPrefix() + "現在の設定値は" + ChatColor.GOLD + ChatColor.UNDERLINE + config.getInt(configType.getName()) + "人" + ChatColor.RESET + ChatColor.GRAY + "に設定されています。");
                        return;
                    } else {
                        sender.sendMessage(Main.getSecondaryPrefix() + "この機能は" + ChatColor.RED + ChatColor.UNDERLINE + "無効" + ChatColor.RESET + ChatColor.GRAY + "になっています。");
                        return;
                    }
                }
            }
        }
        sender.sendMessage(Main.getErrorPrefix() + Main.ErrorType.ARGS.getMessage() + "\n" +
                Main.getErrorPrefix() + "使い方:\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " count all <Number / none>" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "全体が参加できる人数を設定\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " count sponsor <Number / none>" + ChatColor.RESET + ChatColor.GRAY + ":\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " count s <Number / none>" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "スポンサーが参加できる人数を設定・機能を無効化\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " count listener <Number / none>" + ChatColor.RESET + ChatColor.GRAY + ":\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " count l <Number / none>" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "リスナーが参加できる人数を設定・機能を無効化");
        return;
    }

    private void runAdd(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration config = Main.getJRConfig();

        final String typeName = "スルー";
        final Main.ConfigType configType = Main.ConfigType.LIST_THROUGH;

        List<String> list = config.getStringList(configType.getName());

        if (args.length != 1) {
            final String uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString();

            if (!list.contains(uuid)) {
                list.add(uuid);
                config.set(configType.getName(), list);
                Main.saveJRConfig();

                sender.sendMessage(Main.getSuccessPrefix() + args[1] + "を" + typeName + "リストに" + ChatColor.GOLD + ChatColor.UNDERLINE + "追加" + ChatColor.RESET + ChatColor.GREEN + "しました。");
                return;
            }
            sender.sendMessage(Main.getErrorPrefix() + args[1] + "はすでに" + typeName + "リストに追加されています。");
            return;
        }
        sender.sendMessage(Main.getErrorPrefix() + Main.ErrorType.ARGS.getMessage() + "\n" +
                Main.getErrorPrefix() + "使い方:\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " add <Name>" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "プレイヤーを" + typeName + "リストに追加");
        return;
    }

    private void runRemove(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration config = Main.getJRConfig();

        final String typeName = "スルー";
        final Main.ConfigType configType = Main.ConfigType.LIST_THROUGH;

        List<String> list = config.getStringList(configType.getName());

        if (args.length != 1) {
            final String uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString();

            if (list.contains(uuid)) {
                list.remove(uuid);
                config.set(configType.getName(), list);
                Main.saveJRConfig();

                sender.sendMessage(Main.getSuccessPrefix() + args[1] + "を" + typeName + "リストから" + ChatColor.GOLD + ChatColor.UNDERLINE + "削除" + ChatColor.RESET + ChatColor.GREEN + "しました。");
                return;
            }
            sender.sendMessage(Main.getErrorPrefix() + args[1] + "はすでに" + typeName + "リストから削除されています。");
            return;
        }
        sender.sendMessage(Main.getErrorPrefix() + Main.ErrorType.ARGS.getMessage() + "\n" +
                Main.getErrorPrefix() + "使い方:\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " remove <Name>" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "プレイヤーを" + typeName + "リストから削除");
        return;
    }

    private void runAllow(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration config = Main.getJRConfig();

        final Main.ConfigType configType = Main.ConfigType.ISENABLED_BLOCK;

        if (!config.getBoolean(configType.getName())) {
            config.set(configType.getName(), true);
            Main.saveJRConfig();
            sender.sendMessage(Main.getSecondaryPrefix() + "連戦を" + ChatColor.GREEN + ChatColor.UNDERLINE + "許可" + ChatColor.RESET + ChatColor.GRAY + "しました。");
            return;
        }
        sender.sendMessage(Main.getErrorPrefix() + "連戦はすでに許可されています。\n" +
                Main.getSecondaryPrefix() + "連戦を禁止するには\"" + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " deny" + ChatColor.RESET + ChatColor.GRAY + "\"と実行してください。");
        return;
    }

    private void runDeny(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration config = Main.getJRConfig();

        final Main.ConfigType configType = Main.ConfigType.ISENABLED_BLOCK;

        if (config.getBoolean(configType.getName())) {
            config.set(configType.getName(), false);
            Main.saveJRConfig();
            sender.sendMessage(Main.getSecondaryPrefix() + "連戦を" + ChatColor.RED + ChatColor.UNDERLINE + "禁止" + ChatColor.RESET + ChatColor.GRAY + "しました。");
            return;
        }
        sender.sendMessage(Main.getErrorPrefix() + "連戦はすでに禁止されています。\n" +
                Main.getSecondaryPrefix() + "連戦を許可するには\"" + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " allow" + ChatColor.RESET + ChatColor.GRAY + "\"と実行してください。");
        return;
    }

    private void runSave(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration config = Main.getJRConfig();

        final String typeName = "連戦";
        final Main.ConfigType configType = Main.ConfigType.LIST_BLOCK;

        List<String> list = config.getStringList(configType.getName());
        final boolean isOpen = config.getBoolean(Main.ConfigType.ISENABLED_MAIN.getName());

        list.clear();
        config.set(configType.getName(), list);

        Date date = new Date();

        Calendar en = Calendar.getInstance();
        en.add(Calendar.MINUTE, -3);
        Date end = en.getTime();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (isOpen && !Main.isAdmin(player)) {
                if (blockMap.containsKey(player.getUniqueId())) {
                    Date d = new Date(blockMap.get(player.getUniqueId()));

                    if (end.compareTo(d) > 0) {
                        list.add(player.getUniqueId().toString());
                    }
                }
            }
        }

        config.set(configType.getName(), list);
        Main.saveJRConfig();

        sender.sendMessage(Main.getSuccessPrefix() + typeName + "リストに" + list.size() + "人のプレイヤーを" + ChatColor.GOLD + ChatColor.UNDERLINE + "追加" + ChatColor.RESET + ChatColor.GREEN + "しました。");
        return;
    }

    private void runReset(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration config = Main.getJRConfig();

        final String typeName = "連戦";
        final Main.ConfigType configType = Main.ConfigType.LIST_BLOCK;

        List<String> list = config.getStringList(configType.getName());

        list.clear();
        config.set(configType.getName(), list);
        Main.saveJRConfig();

        sender.sendMessage(Main.getSuccessPrefix() + typeName + "リストを" + ChatColor.GOLD + ChatColor.UNDERLINE + "リセット" + ChatColor.RESET + ChatColor.GREEN + "しました。");
        return;
    }

    private String getCommandPrefix(CommandSender sender) {
        return sender instanceof Player ? "/" : "";
    }

    private String getDifferenceString(int a, int b) {
        if (a > b)
            return ChatColor.GRAY + "+" + (a - b);
        else if (a < b)
            return ChatColor.GRAY + "-" + (b - a);
        else
            return ChatColor.GRAY + "0";
    }

    private int getDifference(int a, int b) {
        if (a > b)
            return a - b;
        else if (a < b)
            return b - a;
        else
            return 0;
    }

    private String UUIDtoString(List<UUID> uuids) {
        StringBuffer stringBuffer = new StringBuffer();

        for (UUID uuid : uuids) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
            stringBuffer.append(ChatColor.YELLOW + player.getName() + ChatColor.GRAY + ", ");
        }

        String str = stringBuffer.toString();
        return str.length() > 2 ? str.substring(0, str.length() - 2) : "なし";
    }

    private String toString(List<String> strs, boolean isPlayerList) {
        if (isPlayerList) {
            StringBuffer stringBuffer = new StringBuffer();

            for (String str : strs) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(str));
                stringBuffer.append((player.getPlayer() != null ? ChatColor.GREEN : ChatColor.RED) + player.getName() + ChatColor.GRAY + ", ");
            }

            String str = stringBuffer.toString();
            return str.length() > 2 ? str.substring(0, str.length() - 2) : "なし";
        } else {
            StringBuffer stringBuffer = new StringBuffer();

            for (String str : strs)
                stringBuffer.append(ChatColor.YELLOW + str + ChatColor.GRAY + ", ");


            String str = stringBuffer.toString();
            return str.length() > 2 ? str.substring(0, str.length() - 2) : "なし";
        }
    }

    private String toString(String[] strs, boolean isPlayerList) {
        if (isPlayerList) {
            StringBuffer stringBuffer = new StringBuffer();

            for (String str : strs) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(str));
                stringBuffer.append((player.getPlayer() != null ? ChatColor.GREEN : ChatColor.RED) + player.getName() + ChatColor.GRAY + ", ");
            }

            String str = stringBuffer.toString();
            return str.length() > 2 ? str.substring(0, str.length() - 2) : "なし";
        } else {
            StringBuffer stringBuffer = new StringBuffer();

            for (String str : strs)
                stringBuffer.append(ChatColor.YELLOW + str + ChatColor.GRAY + ", ");


            String str = stringBuffer.toString();
            return str.length() > 2 ? str.substring(0, str.length() - 2) : "なし";
        }
    }
}
