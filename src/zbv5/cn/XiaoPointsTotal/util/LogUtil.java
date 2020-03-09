package zbv5.cn.XiaoPointsTotal.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import zbv5.cn.XiaoPointsTotal.Main;
import zbv5.cn.XiaoPointsTotal.lang.Lang;

import java.io.*;

public class LogUtil
{
    public static void CheckLog()
    {
        if(FileUtil.PlayerPointsListener)
        {
            if(FileUtil.logs)
            {
                createDataFolder();
                PrintUtil.PrintConsole("&alog流水记录开启.");
            } else {
                PrintUtil.PrintConsole("&clog流水记录关闭. 原因:已禁用");
            }
        }
    }



    public static void createDataFolder()
    {
        File logs = new File(Main.getInstance().getDataFolder(), "logs");
        if (!logs.exists())
        {
            logs.mkdirs();
        }
        File month = new File(Main.getInstance().getDataFolder() + "//logs", DateUtil.getDate("yyyy-MM"));
        if (!month.exists())
        {
            month.mkdirs();
        }
        File total = new File(Main.getInstance().getDataFolder() + "//logs/"+DateUtil.getDate("yyyy-MM"),"total.yml");
        if (!total.exists())
        {
            try {
                total.createNewFile();
                FileConfiguration config = YamlConfiguration.loadConfiguration(total);
                config.set("Add", 0);
                config.set("Subtract",0);
                config.save(total);
            }
            catch (Exception e)
            {
                PrintUtil.PrintConsole("&3log文件total.yml &c创建出现问题!");
                e.printStackTrace();
            }
        }

        File record = new File(Main.getInstance().getDataFolder() + "//logs/"+DateUtil.getDate("yyyy-MM"),"record.yml");
        if (!record.exists())
        {
            try {
                record.createNewFile();
            }
            catch (Exception e)
            {
                PrintUtil.PrintConsole("&3log文件record.yml &c创建出现问题!");
                e.printStackTrace();
            }
        }
    }


    public static void log(Player p, int points, Boolean add, String because)
    {
        if(!FileUtil.logs) return;
        createDataFolder();
        File total = new File(Main.getInstance().getDataFolder() + "//logs/"+DateUtil.getDate("yyyy-MM"),"total.yml");
        File record = new File(Main.getInstance().getDataFolder() + "//logs/"+DateUtil.getDate("yyyy-MM"),"record.yml");
        try {
            FileConfiguration config_total = YamlConfiguration.loadConfiguration(total);
            BufferedWriter bw = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (record,true),"UTF-8"));
            if(add)
            {
                bw.write(PrintUtil.HookVariable(p,Lang.Log_Format.replace("<type>",Lang.Log_Add).replace("<log_timeformat>",DateUtil.getDate(Lang.Log_TimeFormat)) .replace("<points>",Integer.toString(points)) .replace("<because>",because)));
                int Add = config_total.getInt("Add") + points;
                config_total.set("Add",Add);
                if(config_total.getString("total."+p.getName()+".uuid") == null)
                {
                    config_total.set("total."+p.getName()+".uuid",p.getUniqueId().toString());
                    config_total.set("total."+p.getName()+".Add",points);
                    config_total.set("total."+p.getName()+".Subtract",0);
                } else {
                    int NewAdd = config_total.getInt("total."+p.getName()+".Add") + points;
                    config_total.set("total."+p.getName()+".Add",NewAdd);
                }
            } else {
                bw.write(PrintUtil.HookVariable(p,Lang.Log_Format.replace("<type>",Lang.Log_Subtract).replace("<log_timeformat>",DateUtil.getDate(Lang.Log_TimeFormat)) .replace("<points>",Integer.toString(points)) .replace("<because>",because)));
                int Subtract = config_total.getInt("Subtract") + Math.abs(points);
                config_total.set("Subtract",Subtract);

                if(config_total.getString("total."+p.getName()+".uuid") == null)
                {
                    config_total.set("total."+p.getName()+".uuid",p.getUniqueId().toString());
                    config_total.set("total."+p.getName()+".Add",0);
                    config_total.set("total."+p.getName()+".Subtract",points);
                } else {
                    int NewAdd = config_total.getInt("total."+p.getName()+".Subtract") + Math.abs(points);
                    config_total.set("total."+p.getName()+".Subtract",NewAdd);
                }
            }
            bw.flush();
            bw.close();
            config_total.save(total);
        }  catch (Exception e)
        {
            PrintUtil.PrintConsole("&3log &c写入出现问题!");
            e.printStackTrace();
        }
    }
}
