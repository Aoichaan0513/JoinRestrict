package org.aoichaan0513.joinrestrict;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class JRCmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (Main.isAdmin(sender)) {
                if (args.length != 0) {
                    if (args[0].equalsIgnoreCase("open")) {
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
                if (args[0].equalsIgnoreCase("open")) {
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
                        return Arrays.asList("open", "close", "count", "add", "remove", "allow", "deny", "save", "reset");
                    } else {
                        if ("open".startsWith(args[0])) {
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
                }
            }
        } else {
            if (args.length == 1) {
                if (args[0].length() == 0) {
                    return Arrays.asList("open", "close", "count", "add", "remove", "allow", "deny", "save", "reset");
                } else {
                    if ("open".startsWith(args[0])) {
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
            }
        }
        return null;
    }

    private HashMap<UUID, Long> blockMap = new HashMap<>();

    private void sendHelpMessage(CommandSender sender, String label) {
        sender.sendMessage(Main.getErrorPrefix() + Main.ErrorType.ARGS.getMessage() + "\n" +
                Main.getErrorPrefix() + "使い方:\n" +
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

    private void runOpen(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration config = Main.getJRConfig();

        final Main.ConfigType configType = Main.ConfigType.ISENABLED_MAIN;

        if (!config.getBoolean(configType.getName())) {
            config.set(configType.getName(), true);
            Main.saveJRConfig();

            Date date = new Date();

            for (Player player : Bukkit.getOnlinePlayers())
                if (!Main.isAdmin(player))
                    blockMap.put(player.getUniqueId(), date.getTime());

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
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " all <Number / none>" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "全体が参加できる人数を設定\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " sponsor <Number / none>" + ChatColor.RESET + ChatColor.GRAY + ":\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " s <Number / none>" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "スポンサーが参加できる人数を設定・機能を無効化\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " listener <Number / none>" + ChatColor.RESET + ChatColor.GRAY + ":\n" +
                Main.getErrorPrefix() + ChatColor.YELLOW + ChatColor.UNDERLINE + getCommandPrefix(sender) + label + " l <Number / none>" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.GOLD + "リスナーが参加できる人数を設定・機能を無効化");
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

        list.clear();
        config.set(configType.getName(), list);

        Date date = new Date();

        Calendar en = Calendar.getInstance();
        en.add(Calendar.MINUTE, -3);
        Date end = en.getTime();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!Main.isAdmin(player)) {
                if (blockMap.containsKey(player.getUniqueId())) {
                    Date d = new Date(blockMap.get(player.getUniqueId()));

                    sender.sendMessage(end.getTime() + ", " + d.getTime() + ": " + end.compareTo(d));

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
}
