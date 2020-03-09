package zbv5.cn.XiaoPointsTotal.util;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import zbv5.cn.XiaoPointsTotal.Main;

public class PlaceholderAPIHook extends EZPlaceholderHook
{
    public PlaceholderAPIHook(Main xiaopointstotal)
    {
        super(xiaopointstotal, "xiaopointstotal");
    }

    public String onPlaceholderRequest(Player p, String s)
    {
        if (s.equalsIgnoreCase("total_all"))
        {
            return DataUtil.getPlayerDate(p,"total","all");
        }
        if (s.equalsIgnoreCase("total_month"))
        {
            return DataUtil.getPlayerDate(p,"total","month");
        }
        if (s.equalsIgnoreCase("total_week"))
        {
            return DataUtil.getPlayerDate(p,"total","week");
        }
        if (s.equalsIgnoreCase("total_day"))
        {
            return DataUtil.getPlayerDate(p,"total","day");
        }
        if (s.equalsIgnoreCase("total_first"))
        {
            return DataUtil.getPlayerDate(p,"total","first");
        }

        if (s.equalsIgnoreCase("date_FirstDay"))
        {
            return DataUtil.getPlayerDate(p,"date","FirstDay");
        }
        if (s.equalsIgnoreCase("date_LastDay"))
        {
            return DataUtil.getPlayerDate(p,"date","LastDay");
        }
        return "";
    }
}
