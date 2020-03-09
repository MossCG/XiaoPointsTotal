package zbv5.cn.XiaoPointsTotal.store;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import zbv5.cn.XiaoPointsTotal.Main;
import zbv5.cn.XiaoPointsTotal.lang.Lang;
import zbv5.cn.XiaoPointsTotal.util.DataUtil;
import zbv5.cn.XiaoPointsTotal.util.DateUtil;
import zbv5.cn.XiaoPointsTotal.util.PrintUtil;

import java.io.File;
import java.util.List;

public class Yml
{

    public static YamlConfiguration data;

    public static void createData()
    {
        File PlayerData = new File(Main.getInstance().getDataFolder(), "data.yml");
        if (!PlayerData.exists())
        {
            try {
                PlayerData.createNewFile();
            }
            catch (Exception e)
            {
                PrintUtil.PrintConsole("&3玩家数据 &c创建出现问题!");
                e.printStackTrace();
            }
        }
        data = YamlConfiguration.loadConfiguration(PlayerData);
        PrintUtil.PrintConsole("&3data.yml &a加载.");
    }

    public static void getPlayerData(Player p)
    {
        int all = 0;
        int month = 0;
        int week = 0;
        int day = 0;
        int first = 0;
        String FirstDay = Lang.NoTotal;
        String LastDay = Lang.NoTotal;
        if(data.getString(p.getName()+".uuid") != null)
        {
            first = data.getInt(p.getName()+".total.first");
            FirstDay = data.getString(p.getName()+".date.FirstDay");
            LastDay = data.getString(p.getName()+".date.LastDay");
            String[] dates = LastDay.split("/");
            //总累计
            all = data.getInt(p.getName()+".total.all");
            //当天
            if(DateUtil.getDate("yyyy/MM/dd").equals(LastDay))
            {
                day = data.getInt(p.getName()+".total.day");
            }
            //判断周
            List<String> WeekList = DateUtil.getWeekDate();
            if(WeekList.contains(LastDay))
            {
                week =  data.getInt(p.getName()+".total.week");
            }
            //本年
            if((DateUtil.getDate("yyyy").equals(dates[0]) && (DateUtil.getDate("MM").equals(dates[1]))))
            {
                month = data.getInt(p.getName()+".total.month");
            }

        }
        DataUtil.setCache(p,all,month,week,day,first,FirstDay,LastDay);
    }

    public static void addPlayerData(Player p,int change)
    {
        File PlayerData = new File(Main.getInstance().getDataFolder(), "data.yml");

        try {
            if(data.getString(p.getName()+".uuid") == null)
            {
                data.set(p.getName()+".uuid", p.getUniqueId().toString());
                //点券累计
                data.set(p.getName()+".total.all", change);
                data.set(p.getName()+".total.month", change);
                data.set(p.getName()+".total.week", change);
                data.set(p.getName()+".total.day", change);
                data.set(p.getName()+".total.first", change);
                //日期
                data.set(p.getName()+".date.FirstDay", DateUtil.getDate(Lang.FirstTotalFormat));
                data.set(p.getName()+".date.LastDay", DateUtil.getDate("yyyy/MM/dd"));
            } else {
                String LastDay = DataUtil.getPlayerDate(p,"date","LastDay");
                String[] dates = LastDay.split("/");
                //总
                int new_all = Integer.parseInt(DataUtil.getPlayerDate(p,"total","all")) + change;
                data.set(p.getName()+".total.all", new_all);
                //当天
                if(DateUtil.getDate("yyyy/MM/dd").equals(LastDay))
                {
                    int new_day = Integer.parseInt(DataUtil.getPlayerDate(p,"total","day")) + change;
                    data.set(p.getName()+".total.day", new_day);
                } else {
                    data.set(p.getName()+".total.day", change);
                    data.set(p.getName()+".date.LastDay", DateUtil.getDate("yyyy/MM/dd"));
                }
                //判断周
                List<String> WeekList = DateUtil.getWeekDate();
                if(WeekList.contains(LastDay))
                {
                    int new_week = Integer.parseInt(DataUtil.getPlayerDate(p,"total","week")) + change;
                    data.set(p.getName()+".total.week", new_week);
                } else {
                    data.set(p.getName()+".total.week", change);
                }
                //本年
                if((DateUtil.getDate("yyyy").equals(dates[0]) && (DateUtil.getDate("MM").equals(dates[1]))))
                {
                    int new_month = Integer.parseInt(DataUtil.getPlayerDate(p,"total","month")) + change;
                    data.set(p.getName()+".total.month", new_month);
                } else {
                    data.set(p.getName()+".total.month", change);
                }
            }
            data.save(PlayerData);
            data = YamlConfiguration.loadConfiguration(PlayerData);
            getPlayerData(p);
        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("&3玩家数据 &c修改储存出现问题!");
            e.printStackTrace();
        }
    }

    public static void getRank()
    {

    }
}
