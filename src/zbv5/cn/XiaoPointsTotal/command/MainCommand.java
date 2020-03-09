package zbv5.cn.XiaoPointsTotal.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import zbv5.cn.XiaoPointsTotal.lang.Lang;
import zbv5.cn.XiaoPointsTotal.util.DataUtil;
import zbv5.cn.XiaoPointsTotal.util.FileUtil;
import zbv5.cn.XiaoPointsTotal.util.LogUtil;
import zbv5.cn.XiaoPointsTotal.util.PrintUtil;

import java.util.regex.Pattern;

public class MainCommand implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if (args.length == 0)
        {
            PrintUtil.PrintCommandSender(sender,false,"&6========= [&bXiaoPointsTotal&6] =========");
            PrintUtil.PrintCommandSender(sender,false,"&6/XiaoPointsTotal &ainfo &7- &b查询本人数据");
            PrintUtil.PrintCommandSender(sender,false,"&6/XiaoPointsTotal &ainfo &e<玩家> &7- &b查询玩家数据");
            PrintUtil.PrintCommandSender(sender,false,"&6/XiaoPointsTotal &aadd &e<玩家> <数量> &7- &b为玩家增加累计值");
            PrintUtil.PrintCommandSender(sender,false,"&6/XiaoPointsTotal &alog &e<玩家> <数量> <形式> <原因>&7- &b为玩家增加累计值");
            PrintUtil.PrintCommandSender(sender,false,"&6/XiaoPointsTotal &areload &7- &c重载配置文件");
            return true;
        }
        if(args[0].equalsIgnoreCase("info"))
        {
            if(!sender.hasPermission("XiaoPointsTotal.info"))
            {
                PrintUtil.PrintCommandSender(sender,true,Lang.NoPermission);
                return false;
            }
            if (args.length == 1)
            {
                if (!(sender instanceof Player))
                {
                    PrintUtil.PrintCommandSender(sender,true,Lang.NeedPlayer);
                    return false;
                }
                Player p = (Player)sender;
                for(String l: Lang.Command_PlayerInfo)
                {
                    l = PrintUtil.HookVariable(p,l);
                    PrintUtil.PrintCommandSender(sender,false,l);
                }
                return true;
            }
            if (args.length == 2)
            {
                Player p = Bukkit.getPlayer(args[1]);
                if(p == null)
                {
                    PrintUtil.PrintCommandSender(sender,true,Lang.NullPlayer);
                    return false;
                }
                for(String l: Lang.Command_PlayerInfo)
                {
                    l = PrintUtil.HookVariable(p,l);
                    PrintUtil.PrintCommandSender(sender,false,l);
                }
                return true;
            }
            PrintUtil.PrintCommandSender(sender,true,"&c格式错误,正确格式/XiaoPointsTotal info &e<玩家>");
            return false;
        }
        if(args[0].equalsIgnoreCase("add"))
        {
            if(!sender.hasPermission("XiaoPointsTotal.add"))
            {
                PrintUtil.PrintCommandSender(sender,true,Lang.NoPermission);
                return false;
            }
            if (args.length == 3)
            {
                Player p = Bukkit.getPlayer(args[1]);
                if(p == null)
                {
                    PrintUtil.PrintCommandSender(sender,true,Lang.NullPlayer);
                    return false;
                }
                Pattern pattern = Pattern.compile("[0-9]*");
                if(!pattern.matcher(args[2]).matches())
                {
                    PrintUtil.PrintCommandSender(sender,true,Lang.NoInteger);
                    return false;
                }
                int points = Integer.parseInt(args[2]);
                if(points <= 0)
                {
                    PrintUtil.PrintCommandSender(sender,true,Lang.NoMore);
                    return false;
                }
                DataUtil.addPlayerDate(p,points);
                PrintUtil.PrintCommandSender(sender,true,Lang.Executed);
                return true;
            }
            PrintUtil.PrintCommandSender(sender,true,"&c格式错误,正确格式/XiaoPointsTotal add <玩家> <数量>");
            return false;
        }
        if(args[0].equalsIgnoreCase("log"))
        {
            if(!sender.hasPermission("XiaoPointsTotal.log"))
            {
                PrintUtil.PrintCommandSender(sender,true,Lang.NoPermission);
                return false;
            }
            if (args.length == 5)
            {
                Player p = Bukkit.getPlayer(args[1]);
                if(p == null)
                {
                    PrintUtil.PrintCommandSender(sender,true,Lang.NullPlayer);
                    return false;
                }
                Pattern pattern = Pattern.compile("[0-9]*");
                if(!pattern.matcher(args[2]).matches())
                {
                    PrintUtil.PrintCommandSender(sender,true,Lang.NoInteger);
                    return false;
                }
                int points = Integer.parseInt(args[2]);
                if(!(args[3].equals("true") || args[3].equals("false")))
                {
                    PrintUtil.PrintCommandSender(sender,true,Lang.NoBoolean);
                    return false;
                }
                Boolean add = Boolean.parseBoolean(args[3]);
                LogUtil.log(p,points,add,args[4]);
                PrintUtil.PrintCommandSender(sender,true,Lang.Executed);
                return true;
            }
            PrintUtil.PrintCommandSender(sender,true,"&c格式错误,正确格式/XiaoPointsTotal log <玩家> <数量> <形式> <原因>");
            return false;
        }
        if(args[0].equalsIgnoreCase("reload"))
        {
            if(!sender.hasPermission("XiaoPointsTotal.reload"))
            {
                PrintUtil.PrintCommandSender(sender,true,Lang.NoPermission);
                return false;
            }
            try
            {
                FileUtil.LoadFile();
                PrintUtil.PrintCommandSender(sender,true,Lang.SuccessReload);
                return true;
            } catch (Exception e)
            {
                PrintUtil.PrintCommandSender(sender,true,Lang.FailReload);
                e.printStackTrace();
            }
            return false;
        }
        PrintUtil.PrintCommandSender(sender,true,Lang.NullCommand);
        return false;
    }
}
