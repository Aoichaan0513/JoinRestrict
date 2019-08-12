package org.aoichaan0513.joinrestrict;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static JavaPlugin javaPlugin;
    private static FileConfiguration fileConfiguration;

    @Override
    public void onEnable() {
        javaPlugin = this;

        saveDefaultConfig();
        fileConfiguration = getConfig();

        getCommand("jr").setExecutor(new JRCmd());

        Bukkit.getPluginManager().registerEvents(new JRListener(), this);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public static JavaPlugin getInstance() {
        return javaPlugin;
    }

    public static boolean isAdmin(CommandSender sender) {
        if (sender instanceof Player) {
            Player sp = (Player) sender;
            return sp.isOp() || sp.hasPermission("jr.cmd");
        } else {
            return true;
        }
    }

    public static void saveJRConfig() {
        getInstance().saveConfig();
        fileConfiguration = getInstance().getConfig();
    }

    public static FileConfiguration getJRConfig() {
        return getJRConfig(true);
    }

    public static FileConfiguration getJRConfig(boolean b) {
        if (b)
            fileConfiguration = getInstance().getConfig();
        return fileConfiguration;
    }

    public static String getPrefix(ChatColor color) {
        return color + "> ";
    }

    public static String getPrimaryPrefix() {
        return getPrefix(ChatColor.BLUE);
    }

    public static String getSecondaryPrefix() {
        return getPrefix(ChatColor.GRAY);
    }

    public static String getSuccessPrefix() {
        return getPrefix(ChatColor.GREEN);
    }

    public static String getWarningPrefix() {
        return getPrefix(ChatColor.GOLD);
    }

    public static String getErrorPrefix() {
        return getPrefix(ChatColor.RED);
    }

    public enum ConfigType {
        ISENABLED_MAIN("isEnabled.main"),
        ISENABLED_BLOCK("isEnabled.block"),
        MAXPLAYERS_ALL("maxPlayers.all"),
        MAXPLAYERS_SPONSOR("maxPlayers.sponsor"),
        MAXPLAYERS_LISTENER("maxPlayers.listener"),
        LIST_THROUGH("list.through"),
        LIST_BLOCK("list.block");

        private final String name;

        private ConfigType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum ErrorType {
        ARGS("引数が不正です。"),
        PERMISSION("権限がありません。"),
        PLAYER("プレイヤー以外からは実行できません。"),
        NUMBER("引数には数値を指定してください。"),
        CLOSED(ChatColor.RED + "サーバーが閉鎖されました。"),
        KICKED_CLOSED(ChatColor.RED + "サーバーが開放されていないため参加することが出来ません。"),
        KICKED_FULL(ChatColor.RED + "サーバーが満員のため参加することが出来ません。");

        private final String msg;

        private ErrorType(String msg) {
            this.msg = msg;
        }

        public String getMessage() {
            return msg;
        }
    }
}
