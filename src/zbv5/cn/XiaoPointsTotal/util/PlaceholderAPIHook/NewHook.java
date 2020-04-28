package zbv5.cn.XiaoPointsTotal.util.PlaceholderAPIHook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import zbv5.cn.XiaoPointsTotal.Main;
import zbv5.cn.XiaoPointsTotal.util.DataUtil;

public class NewHook extends PlaceholderExpansion
{
    private final Main plugin;

    public NewHook(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor()
    {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier() {
        return "xiaopointstotal";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player p, String s) {

        if (p == null) return "null_player";
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