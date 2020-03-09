package zbv5.cn.XiaoPointsTotal.listener;

import org.black_ixx.playerpoints.event.PlayerPointsChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import zbv5.cn.XiaoPointsTotal.util.DataUtil;
import zbv5.cn.XiaoPointsTotal.util.FileUtil;

import java.util.UUID;

public class PlayerListener implements Listener
{
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        if(DataUtil.total_all.containsKey(p.getName()))
        {
            DataUtil.removeCache(p);
        }
    }

}
