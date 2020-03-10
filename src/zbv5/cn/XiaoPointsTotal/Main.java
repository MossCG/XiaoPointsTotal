package zbv5.cn.XiaoPointsTotal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import zbv5.cn.XiaoPointsTotal.bStats.Metrics;
import zbv5.cn.XiaoPointsTotal.command.MainCommand;
import zbv5.cn.XiaoPointsTotal.listener.PlayerListener;
import zbv5.cn.XiaoPointsTotal.listener.PointsListener;
import zbv5.cn.XiaoPointsTotal.util.*;

/**
 * @author wow_xiaoyao
 */

public class Main extends JavaPlugin
{
    private static Main instance;
    public static boolean PlaceholderAPI = false;
    public static boolean PlayerPoints = false;

    public static Main getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        instance = this;
        PrintUtil.PrintConsole("&e======== &bXiaoPointsTotal &e> &d开始加载 &e========");
        if (Bukkit.getPluginManager().isPluginEnabled("PlayerPoints"))
        {
            PlayerPoints = true;
            getServer().getPluginManager().registerEvents(new PointsListener(), this);
            PrintUtil.PrintConsole("&a检测到前置插件PlayerPoints");
        } else {
            PrintUtil.PrintConsole("&c未检测到前置插件PlayerPoints,部分功能已失效.");
        }

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
        {
            PlaceholderAPI = true;
            new PlaceholderAPIHook(this).hook();
            PrintUtil.PrintConsole("&a检测到前置插件PlaceholderAPI");
            //保留HOOK
        } else {
            PrintUtil.PrintConsole("&c未检测到前置插件PlaceholderAPI,部分功能已失效.");
        }
        FileUtil.LoadFile();
        DataUtil.CheckDataStore();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getCommand("XiaoPointsTotal").setExecutor(new MainCommand());

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
        {
            public void run()
            {
                if(DateUtil.getDate("HH:mm:ss").equals("00:00:00"))
                {
                    for(Player p:Bukkit.getServer().getOnlinePlayers())
                    {
                        DataUtil.removeCache(p);
                    }
                }
            }
        }, 20L, 20L);

        int pluginId = 6726;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));

        PrintUtil.PrintConsole("&e======== &bXiaoPointsTotal &e> &a加载成功 &e========");
    }

    @Override
    public void onDisable()
    {
        PrintUtil.PrintConsole("&c插件卸载");
    }
}
