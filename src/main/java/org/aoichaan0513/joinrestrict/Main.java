package org.aoichaan0513.joinrestrict;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {

    private static JavaPlugin javaPlugin;
    private static FileConfiguration fileConfiguration;

    public static List<UUID> list = new ArrayList<>();

    public static List<UUID> sponsorList = new ArrayList<>();
    public static List<UUID> listenerList = new ArrayList<>();

    @Override
    public void onEnable() {
        javaPlugin = this;

        saveDefaultConfig();
        fileConfiguration = getConfig();

        Bukkit.getConsoleSender().sendMessage(Main.getSecondaryPrefix() + "プラグインを起動しました。");

        getCommand("jr").setExecutor(new JRCmd());
        Bukkit.getPluginManager().registerEvents(new JRListener(), this);

        String result = Main.sendPost("https://yschecker.aoichaan0513.xyz/api", "{\"type\":\"getSponsors\"}");
        System.out.println(result.substring(0, result.length() - 2) + "]");
        JSONArray jsonArray = new JSONArray(result.substring(0, result.length() - 2) + "]");
        for (Object object : jsonArray.toList()) {
            list.add(UUID.fromString(String.valueOf(object)));
            System.out.println(object);
        }
    }

    @Override
    public void onDisable() {
        saveConfig();
        Bukkit.getConsoleSender().sendMessage(Main.getSecondaryPrefix() + "プラグインを終了しました。");
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

    public static boolean isAdmin(Player player) {
        return player.isOp() || player.hasPermission("jr.cmd");
    }

    public static boolean isAdmin(OfflinePlayer player) {
        return player.isOp();
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

    public static String sendPost(String strPostUrl, String json) {
        HttpURLConnection con = null;
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(strPostUrl);
            con = (HttpURLConnection) url.openConnection();
            // HTTPリクエストコード
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "jp");
            con.setRequestProperty("Content-Type", "application/JSON; charset=utf-8");
            con.setRequestProperty("User-agent", "SponsorChecker_Plugin");
            con.setRequestProperty("Content-Length", String.valueOf(json.length()));
            // リクエストのbodyにJSON文字列を書き込む
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(json);
            out.flush();
            con.connect();

            final int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    result.append(line);
                }
                bufReader.close();
                inReader.close();
                in.close();
            } else {
                System.out.println(status);
            }

        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return result.toString();
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
        KICKED_FULLED(ChatColor.RED + "サーバーが満員のため参加することが出来ません。"),
        KICKED_BLOCKED(ChatColor.RED + "あなたは前回参加したため参加することが出来ません。"),
        KICKED_OTHER(ChatColor.RED + "現在参加することが出来ません。");

        private final String msg;

        private ErrorType(String msg) {
            this.msg = msg;
        }

        public String getMessage() {
            return msg;
        }
    }
}
