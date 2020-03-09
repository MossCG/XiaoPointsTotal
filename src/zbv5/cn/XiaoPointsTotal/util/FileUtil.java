package zbv5.cn.XiaoPointsTotal.util;

import org.bukkit.configuration.file.YamlConfiguration;
import zbv5.cn.XiaoPointsTotal.Main;
import zbv5.cn.XiaoPointsTotal.lang.Lang;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil
{
    public static YamlConfiguration config;
    public static YamlConfiguration lang;
    public static String Mode = null;
    public static Boolean PlayerPointsListener = false;
    public static Boolean logs = false;
    public static List<String> Run_Format = new ArrayList<>();

    public static void LoadFile()
    {
        File Lang_Yml = new File(Main.getInstance().getDataFolder(), "lang.yml");
        if (!Lang_Yml.exists())
        {
            Main.getInstance().saveResource("lang.yml", false);
        }
        lang = YamlConfiguration.loadConfiguration(Lang_Yml);
        PrintUtil.PrintConsole("&3lang.yml &a加载.");
        Lang.LoadLang();

        File Config_Yml = new File(Main.getInstance().getDataFolder(), "config.yml");
        if (!Config_Yml.exists())
        {
            Main.getInstance().saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(Config_Yml);
        PrintUtil.PrintConsole("&3config.yml &a加载.");
        PlayerPointsListener = config.getBoolean("PlayerPointsListener.Enable");
        logs  = config.getBoolean("PlayerPointsListener.logs");
        Run_Format = config.getStringList("PlayerPointsListener.Format");
        CheckMode();
    }

    public static void CheckMode()
    {
        if(!Main.PlayerPoints)
        {
            PrintUtil.PrintConsole("&c监听关闭. 原因:未安装插件");
            return;
        }
        if(!PlayerPointsListener)
        {
            PrintUtil.PrintConsole("&c监听关闭. 原因:未启用功能");
            return;
        }
        if(config.getString("PlayerPointsListener.Mode").equals("Add"))
        {
            Mode = "Add";
        }
        if(config.getString("PlayerPointsListener.Mode").equals("Subtract"))
        {
            Mode = "Subtract";
        }
        LogUtil.CheckLog();
        if(Mode != null)
        {
            PrintUtil.PrintConsole("&3"+Mode+"模式 &a启动.");
        } else {
            PrintUtil.PrintConsole("&c累计模式异常,累计功能失效.");
        }
    }
}
