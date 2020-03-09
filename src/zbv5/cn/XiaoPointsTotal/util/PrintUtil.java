package zbv5.cn.XiaoPointsTotal.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import zbv5.cn.XiaoPointsTotal.Main;
import zbv5.cn.XiaoPointsTotal.lang.Lang;

import java.util.List;

public class PrintUtil
{

    public static void PrintConsole(String s)
    {
            Bukkit.getConsoleSender().sendMessage(cc(Lang.Prefix+s));
    }

    public static void PrintCommandSender(CommandSender sender,Boolean prefix, String s)
    {
        if(prefix)
        {
            sender.sendMessage(cc(Lang.Prefix+s));
        } else {
            sender.sendMessage(cc(s));
        }
    }

    public static String cc(String s)
    {
        s = ChatColor.translateAlternateColorCodes('&', s);
        return s;
    }



    public static String HookVariable(Player p,String s)
    {
        s = cc(s);

        s = s.replace("<Prefix>",Lang.Prefix);

        s = s.replace("<player>",p.getName());
        s = s.replace("<player_DisplayName>",p.getDisplayName());

        s = s.replace("<date_FirstDay>",DataUtil.getPlayerDate(p,"date","FirstDay"));
        s = s.replace("<date_LastDay>",DataUtil.getPlayerDate(p,"date","LastDay"));
        s = s.replace("<total_all>",DataUtil.getPlayerDate(p,"total","all"));
        s = s.replace("<total_month>",DataUtil.getPlayerDate(p,"total","month"));
        s = s.replace("<total_week>",DataUtil.getPlayerDate(p,"total","week"));
        s = s.replace("<total_day>",DataUtil.getPlayerDate(p,"total","day"));
        s = s.replace("<total_first>",DataUtil.getPlayerDate(p,"total","first"));
        return s;
    }

    public static void Run(List<String> list,Player p,int points)
    {
        for(String ss:list)
        {
            //Hook PlaceholderAPI
            if(Main.PlaceholderAPI)
            {
                ss = PlaceholderAPI.setPlaceholders(p, ss);
            }

            //Hook 自身变量
            ss = HookVariable(p,ss);

            //Hook 监听执行点券数量变量  合并执行方式
            ss = ss.replace("<this_points>",Integer.toString(points));
            ss = ss.replace("<this_abs_points>",(Integer.toString(Math.abs(points))));

            //执行操作
            if(ss.startsWith("[message]"))
            {
                ss=ss.replace("[message]", "");
                p.sendMessage( cc(ss));
            }
            if(ss.startsWith("[bc]"))
            {
                ss=ss.replace("[bc]", "");
                Bukkit.getServer().broadcastMessage(ss);
            }
            if(ss.startsWith("[op]"))
            {
                boolean op = p.isOp();
                p.setOp(true);
                try
                {
                    ss=ss.replace("[op]", "");
                    p.chat("/"+ss);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                try
                {
                    p.setOp(op);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    p.setOp(false);
                }
            }
            if(ss.startsWith("[player]"))
            {
                ss=ss.replace("[player]", "");
                p.performCommand(ss);
            }
            if(ss.startsWith("[console]"))
            {
                ss=ss.replace("[console]", "");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
            }
        }
    }
}
