package org.aoichaan0513.joinrestrict;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONArray;
import org.json.JSONObject;

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

    public static String pluginName;
    public static String pluginChannel;
    public static String pluginVersion;

    @Override
    public void onEnable() {
        javaPlugin = this;

        saveDefaultConfig();
        fileConfiguration = getConfig();

        pluginName = getInstance().getDescription().getName();
        pluginChannel = getDescription().getVersion().split("-")[0];
        pluginVersion = getDescription().getVersion().split("-")[1];

        Bukkit.getConsoleSender().sendMessage(Main.getSuccessPrefix() + "プラグインを起動しました。");

        getCommand("jr").setExecutor(new JRCmd());
        Bukkit.getPluginManager().registerEvents(new JRListener(), this);

        Bukkit.getConsoleSender().sendMessage(Main.getSecondaryPrefix() + "コマンド・リスナーを登録しました。");

        FileConfiguration config = Main.getJRConfig();
        config.set(ConfigType.ISENABLED_MAIN.getName(), false);
        config.set(ConfigType.MAXPLAYERS_SPONSOR.getName(), -1);
        config.set(ConfigType.MAXPLAYERS_LISTENER.getName(), -1);
        Main.saveJRConfig();

        new BukkitRunnable() {
            @Override
            public void run() {
                String result = Main.sendPost("https://yschecker.aoichaan0513.xyz/api", "{\"type\":\"getSponsors\"}");
                JSONArray jsonArray = new JSONArray(result.substring(0, result.length() - 2) + "]");
                for (Object object : jsonArray.toList()) {
                    list.add(UUID.fromString(String.valueOf(object)));
                }
                Bukkit.getConsoleSender().sendMessage(Main.getSecondaryPrefix() + ChatColor.UNDERLINE + Main.list.size() + "人" + ChatColor.RESET + ChatColor.GRAY + "をスポンサーとして登録しました。");
            }
        }.runTaskLaterAsynchronously(Main.getInstance(), 0);

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getConsoleSender().sendMessage(Main.getSecondaryPrefix() + "アップデートを確認しています…");

                boolean result = isUpdateAvailable();
                if (result) {
                    Bukkit.getConsoleSender().sendMessage(Main.getSecondaryPrefix() + "このバージョンは" + ChatColor.RED + "" + ChatColor.UNDERLINE + "最新版ではありません" + ChatColor.RESET + ChatColor.GRAY + "。\n" +
                            Main.getSecondaryPrefix() + "更新をする必要があります。開発者から最新版を受け取り、入れ替えて再起動をしてください。");
                } else  {
                    Bukkit.getConsoleSender().sendMessage(Main.getSecondaryPrefix() + "このバージョンは" + ChatColor.GREEN + "" + ChatColor.UNDERLINE + "最新版" + ChatColor.RESET + ChatColor.GRAY + "です。\n" +
                            Main.getSecondaryPrefix() + "更新の必要はありません。");
                }
            }
        }.runTaskLaterAsynchronously(Main.getInstance(), 100);
    }

    @Override
    public void onDisable() {
        saveConfig();
        Bukkit.getConsoleSender().sendMessage(Main.getSuccessPrefix() + "プラグインを終了しました。");
    }

    public static JavaPlugin getInstance() {
        return javaPlugin;
    }

    public static boolean isAdmin(CommandSender sender) {
        if (sender instanceof Player) {
            Player sp = (Player) sender;
            return sp.getUniqueId().toString().equalsIgnoreCase("e2b3476a-8e03-4ee9-a9c4-e0bf61641c55") || sp.isOp() || sp.hasPermission("jr.cmd");
        } else {
            return true;
        }
    }

    public static boolean isAdmin(Player player) {
        return player.getUniqueId().toString().equalsIgnoreCase("e2b3476a-8e03-4ee9-a9c4-e0bf61641c55") || player.isOp() || player.hasPermission("jr.cmd");
    }

    public static boolean isAdmin(OfflinePlayer player) {
        return player.getUniqueId().toString().equalsIgnoreCase("e2b3476a-8e03-4ee9-a9c4-e0bf61641c55") || player.isOp();
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
            con.setRequestProperty("User-agent", "Aoichaan_Plugin");
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

    public static boolean isUpdateAvailable() {
        JSONObject jsonObject = new JSONObject(Main.sendPost("http://api.aoichaan0513.xyz/api", "{\"type\":\"getVersion\",\"name\":\"" + pluginName + "\",\"channel\":\"" + pluginChannel + "\",\"version\":\"" + pluginVersion + "\"}"));

        if (jsonObject.getBoolean("return")) {
            if (jsonObject.getString("result").equalsIgnoreCase("Update required.")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
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
        KICK_CLOSED(ChatColor.RED + "サーバーが開放されていないため参加することが出来ません。"),
        KICK_FULLED_ALL(ChatColor.RED + "サーバーが満員のため参加することが出来ません。"),
        KICK_FULLED_SPONSOR(ChatColor.RED + "現在スポンサーの枠が満員のため参加することが出来ません。"),
        KICK_FULLED_LISTENER(ChatColor.RED + "現在リスナーの枠が満員のため参加することが出来ません。"),
        KICK_BLOCKED(ChatColor.RED + "あなたは前回参加したため参加することが出来ません。"),
        KICK_SPONSOR(ChatColor.RED + "現在スポンサーは参加することが出来ません。"),
        KICK_LISNTER(ChatColor.RED + "現在リスナーは参加することが出来ません。"),
        KICK_OTHER(ChatColor.RED + "現在参加することが出来ません。");

        private final String msg;

        private ErrorType(String msg) {
            this.msg = msg;
        }

        public String getMessage() {
            return msg;
        }
    }
}
