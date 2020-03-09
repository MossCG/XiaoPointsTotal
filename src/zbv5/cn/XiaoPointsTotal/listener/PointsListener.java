package zbv5.cn.XiaoPointsTotal.listener;

import org.black_ixx.playerpoints.event.PlayerPointsChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import zbv5.cn.XiaoPointsTotal.util.DataUtil;
import zbv5.cn.XiaoPointsTotal.util.FileUtil;
import zbv5.cn.XiaoPointsTotal.util.LogUtil;
import zbv5.cn.XiaoPointsTotal.util.PrintUtil;

import java.util.UUID;

public class PointsListener implements Listener
{
    @EventHandler
    public void PlayerPointChange(PlayerPointsChangeEvent e)
    {
        if(e.getChange() ==0) return;
        if(FileUtil.PlayerPointsListener)
        {
            UUID PlayerUUID = e.getPlayerId();
            Player p = Bukkit.getPlayer(PlayerUUID);
            int points = e.getChange();
            if(p != null)
            {
                if(FileUtil.Mode.equals("Add"))
                {
                    if(points >0)
                    {
                        DataUtil.addPlayerDate(p,points);
                        PrintUtil.Run(FileUtil.Run_Format,p,points);
                    }
                }
                if(FileUtil.Mode.equals("Subtract"))
                {
                    if(points <0)
                    {
                        DataUtil.addPlayerDate(p,Math.abs(points));
                        PrintUtil.Run(FileUtil.Run_Format,p,points);
                    }
                }

                if(points > 0)
                {
                    LogUtil.log(p,points,true,"PlayerPointsChange事件自动监听");
                } else {
                    LogUtil.log(p,points,false,"PlayerPointsChange事件自动监听");
                }
            }
        }
    }
}
