package zbv5.cn.XiaoPointsTotal.lang;

import zbv5.cn.XiaoPointsTotal.util.FileUtil;
import zbv5.cn.XiaoPointsTotal.util.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class Lang
{
    public static String Prefix = "&6[&bXiaoPointsTotal&6]";
    public static String NoTotal = "&c没有充值记录";
    public static String FirstTotalFormat = "yyyy-MM-dd HH:mm:ss";
    public static String NoPermission = "&c你没有权限这样做";
    public static String NullPlayer = "&c玩家不存在或不在线.";
    public static String NeedPlayer = "&c只有玩家才能执行该操作.";
    public static String SuccessReload = "&a重载完成!";
    public static String FailReload = "&c重载失败!请前往控制台查看报错.";
    public static String NoInteger = "&c输入值非整数.";
    public static String NoMore = "&c数量异常.";
    public static String NoBoolean = "&c请输入正确的形式true/false.";
    public static String NullCommand = "&c未知指令.";
    public static String Executed= "&a已执行操作.";
    public static List<String> Command_PlayerInfo = new ArrayList<>();
    public static String Log_TimeFormat = "yyyy年MM月dd日-HH时mm分ss秒";
    public static String Log_Add = "充值";
    public static String Log_Subtract = "消费";
    public static String Log_Format = "<log_timeformat> 玩家:<player> <type>了<points>点券 (原因:<because>)";

    public static void LoadLang()
    {
        try
        {
            Prefix = FileUtil.lang.getString("Prefix");
            NoTotal = FileUtil.lang.getString("NoTotal");
            FirstTotalFormat = FileUtil.lang.getString("FirstTotalFormat");
            Command_PlayerInfo = FileUtil.lang.getStringList("Command_PlayerInfo");
            NoPermission = FileUtil.lang.getString("NoPermission");
            NullPlayer = FileUtil.lang.getString("NullPlayer");
            NeedPlayer = FileUtil.lang.getString("NeedPlayer");
            SuccessReload = FileUtil.lang.getString("SuccessReload");
            FailReload = FileUtil.lang.getString("FailReload");
            NoInteger = FileUtil.lang.getString("NoInteger");
            NoMore = FileUtil.lang.getString("NoMore");
            NoBoolean = FileUtil.lang.getString("NoBoolean");
            NullCommand = FileUtil.lang.getString("NullCommand");
            Executed= FileUtil.lang.getString("Executed");
            Log_TimeFormat= FileUtil.lang.getString("Log.TimeFormat");
            Log_Add= FileUtil.lang.getString("Log.Add");
            Log_Subtract= FileUtil.lang.getString("Log.Subtract");
            Log_Format= FileUtil.lang.getString("Log.Format");
			PrintUtil.PrintConsole("&3lang.yml &a读取完成.");
        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("&3lang.yml &c读取出现问题!");
            e.printStackTrace();
        }
    }

}
