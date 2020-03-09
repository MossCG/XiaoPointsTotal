package zbv5.cn.XiaoPointsTotal.util;

import org.bukkit.entity.Player;
import zbv5.cn.XiaoPointsTotal.store.Mysql;
import zbv5.cn.XiaoPointsTotal.store.Yml;

import java.util.HashMap;

public class DataUtil
{
    public static String Store = "Yml";
    //缓存累计
    public static HashMap<String, Integer> total_all = new HashMap<String, Integer>();
    public static HashMap<String, Integer> total_month = new HashMap<String, Integer>();
    public static HashMap<String, Integer> total_week = new HashMap<String, Integer>();
    public static HashMap<String, Integer> total_day = new HashMap<String, Integer>();
    public static HashMap<String, Integer> total_first = new HashMap<String, Integer>();
    //缓存日期
    public static HashMap<String, String> date_FirstDay = new HashMap<String, String>();
    public static HashMap<String, String> date_LastDay = new HashMap<String, String>();

    public static void CheckDataStore()
    {
        if(FileUtil.config.getString("Store").equals("Yml"))
        {
            Store = "Yml";
            PrintUtil.PrintConsole("&e储存方式: &6"+Store);
            Yml.createData();
            return;
        }
        if(FileUtil.config.getString("Store").equals("Mysql"))
        {
            Store = "Mysql";
            PrintUtil.PrintConsole("&e储存方式: &6"+Store);
            Mysql.createTable();
            return;
        }
        PrintUtil.PrintConsole("&c储存方式异常,请检查配置文件Store配置.");
    }
    public static void getPlayerDateCache(Player p)
    {
        if(Store.equals("Yml"))
        {
            Yml.getPlayerData(p);
        }
        if(Store.equals("Mysql"))
        {
            Mysql.getPlayerData(p);
        }
    }

    public static String getPlayerDate(Player p,String type,String time)
    {
        if(!total_all.containsKey(p.getName()))
        {
            getPlayerDateCache(p);
        }
        if(type.equals("date"))
        {
            if(time.equals("FirstDay"))
            {
                return date_FirstDay.get(p.getName());
            }
            if(time.equals("LastDay"))
            {
                return date_LastDay.get(p.getName());
            }
        }
        if(type.equals("total"))
        {
            if(time.equals("all"))
            {
                return Integer.toString(total_all.get(p.getName()));
            }
            if(time.equals("month"))
            {
                return Integer.toString(total_month.get(p.getName()));
            }
            if(time.equals("week"))
            {
                return Integer.toString(total_week.get(p.getName()));
            }
            if(time.equals("day"))
            {
                return Integer.toString(total_day.get(p.getName()));
            }
            if(time.equals("first"))
            {
                return Integer.toString(total_first.get(p.getName()));
            }
        }
        return "null";
    }

    public static void addPlayerDate(Player p,int points)
    {
        if(Store.equals("Yml"))
        {
            Yml.addPlayerData(p,points);
        }
        if(Store.equals("Mysql"))
        {
            Mysql.addPlayerData(p,points);
        }
    }

    public static void setCache(Player p,int all,int month,int week,int day,int first,String Date_FirstDay,String Date_LastDay)
    {
        total_all.put(p.getName(),all);
        total_month.put(p.getName(),month);
        total_week.put(p.getName(),week);
        total_day.put(p.getName(),day);
        total_first.put(p.getName(),first);
        date_FirstDay.put(p.getName(),Date_FirstDay);
        date_LastDay.put(p.getName(),Date_LastDay);
    }

    public static void removeCache(Player p)
    {
        total_all.remove(p.getName());
        total_month.remove(p.getName());
        total_week.remove(p.getName());
        total_day.remove(p.getName());
        total_first.remove(p.getName());
        date_FirstDay.remove(p.getName());
        date_LastDay.remove(p.getName());
    }

}
